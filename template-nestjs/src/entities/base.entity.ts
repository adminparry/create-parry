import { Expose } from "class-transformer";
import { Column, Entity } from "typeorm";


export class BaseEntity {
    

    @Column()
    create_time:Date;
    @Column()
    update_time:Date;
    @Column()
    create_user:string;
    @Column()
    update_user:string;
    @Column()
    deleted:boolean

}