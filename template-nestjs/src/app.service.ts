import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { User } from './entities/user.entity';

@Injectable()
export class AppService {
  constructor(
    @InjectRepository(User)
    private userRepository: Repository<User>
  ) {}


  getHello(): string {
    return 'Hello World!';
  }


  async getUsers() {
    const users = await this.userRepository.find({
      // select: ['id', 'username', 'email'] 
    });
    return users;
  }
  
}
