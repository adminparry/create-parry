const { Role, Permission } = require('../models');

module.exports = function(requiredPermission) {
  return async (req, res, next) => {
    try {
      // 1. 获取用户角色
      const user = req.user;
      const roles = await user.getRoles({
        include: {
          model: Permission,
          where: {
            name: requiredPermission
          },
          required: false
        }
      });
      
      // 2. 检查权限
      let hasPermission = false;
      for (const role of roles) {
        if (role.Permissions && role.Permissions.length > 0) {
          hasPermission = true;
          break;
        }
      }
      
      if (!hasPermission) {
        return res.status(403).json({ 
          success: false, 
          message: 'Forbidden: Insufficient permissions' 
        });
      }
      
      next();
    } catch (error) {
      console.error('RBAC middleware error:', error);
      res.status(500).json({ success: false, message: 'Internal server error' });
    }
  };
};