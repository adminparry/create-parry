import { Expose } from "class-transformer";
import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";
import { BaseEntity } from "./base.entity";

@Expose()
@Entity("locale")
export class Locale extends BaseEntity {


    @PrimaryGeneratedColumn()
    id: number;
    
    @Column()
    en: string;

    @Column()
    zh: string;


}