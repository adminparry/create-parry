package com.example.demo.utils;

import com.example.demo.foundation.config.SsoConfig;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Component
public class SsoUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private SsoConfig ssoConfig;

    // 初始化验证
    @PostConstruct
    public void init() {
        if (ssoConfig.getSECRET_KEY() == null || ssoConfig.getSECRET_KEY().length() < 12) {
            throw new IllegalStateException("JWT密钥长度必须至少12位");
        }
    }

    /**
     * 生成JWT令牌并存入Redis
     * @param userId 用户ID
     * @param username 用户名
     * @param extraClaims 额外声明信息
     * @return JWT令牌
     */
    public String generateToken(String userId, String username, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ssoConfig.getJWT_EXPIRATION());

        String token = Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .addClaims(extraClaims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, ssoConfig.getSECRET_KEY())
                .compact();

        // 存入Redis，设置过期时间比JWT稍长
        String redisKey = ssoConfig.getREDIS_PREFIX() + userId;
        redisTemplate.opsForValue().set(redisKey, token, ssoConfig.getJWT_EXPIRATION() + 60000, TimeUnit.MILLISECONDS);

        return token;
    }

    /**
     * 验证并解析JWT令牌
     * @param token JWT令牌
     * @return 解析后的声明
     */
    public Claims validateAndParseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(ssoConfig.getSECRET_KEY())
                    .parseClaimsJws(token)
                    .getBody();

            // 检查Redis中是否存在此令牌
            String userId = claims.getSubject();
            String redisKey = ssoConfig.getREDIS_PREFIX() + userId;
            String storedToken = redisTemplate.opsForValue().get(redisKey);

            if (storedToken == null || !storedToken.equals(token)) {
                throw new JwtException("令牌已失效");
            }

            return claims;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("无效的令牌", e);
        }
    }

    /**
     * 使令牌失效
     * @param token JWT令牌
     */
    public void invalidateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(ssoConfig.getSECRET_KEY())
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();
            String redisKey = ssoConfig.getREDIS_PREFIX() + userId;
            redisTemplate.delete(redisKey);
        } catch (JwtException e) {
            // 无效令牌无需处理
        }
    }

    /**
     * 刷新令牌有效期
     * @param token JWT令牌
     * @return 新令牌(可选)
     */
    public String refreshToken(String token) {
        Claims claims = validateAndParseToken(token);

        // 删除旧令牌
        String userId = claims.getSubject();
        String redisKey = ssoConfig.getREDIS_PREFIX() + userId;
        redisTemplate.delete(redisKey);

        // 生成新令牌
        return generateToken(
                userId,
                claims.get("username", String.class),
                new HashMap<>(claims)
        );
    }

    /**
     * 从请求中获取令牌
     * @param request HttpServletRequest
     * @return 令牌或null
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return request.getParameter("token");
    }
}