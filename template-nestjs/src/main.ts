import { ClassSerializerInterceptor, ValidationPipe } from '@nestjs/common';
import { NestFactory, Reflector } from '@nestjs/core';
import { useContainer } from 'class-validator';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.useGlobalInterceptors(new ClassSerializerInterceptor(app.get(Reflector)));
  // 全局验证管道
  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
      whitelist: true, // 自动过滤掉没有装饰器的属性
      forbidNonWhitelisted: true, // 如果有非白名单属性，抛出错误
    }),
  );
  
  // 使class-validator使用NestJS的依赖注入容器
  useContainer(app.select(AppModule), { fallbackOnErrors: true });
  
  const port = process.env.PORT ?? 3100
  await app.listen(port);
  console.log('server running at http://localhost:' + port)

}
bootstrap();
