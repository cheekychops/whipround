import { IContribution } from 'app/shared/model//contribution.model';
import { IWhipround } from 'app/shared/model//whipround.model';

export interface IPerson {
    id?: number;
    userLogin?: string;
    userId?: number;
    contributions?: IContribution[];
    whiprounds?: IWhipround[];
}

export class Person implements IPerson {
    constructor(
        public id?: number,
        public userLogin?: string,
        public userId?: number,
        public contributions?: IContribution[],
        public whiprounds?: IWhipround[]
    ) {}
}
