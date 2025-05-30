import { Controller, Get, UseGuards } from '@nestjs/common';
import { AppService } from './app.service';
import { JwtAuthGuard } from './auth/guards/jwt-auth.guard';
import { User } from './entities/user.entity';
import { CommonResponse } from './utils/commonResponse';


@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @UseGuards(JwtAuthGuard)
  @Get()
  getHello(): string {
    return this.appService.getHello();
  }

  @Get("users")
  async getUsers() {
    const users = await this.appService.getUsers();
    return CommonResponse.successWithData(users);
  }
}
