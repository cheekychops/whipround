import { Moment } from 'moment';
import { IContribution } from 'app/shared/model//contribution.model';
import { IPerson } from 'app/shared/model//person.model';

export const enum WhiproundStatus {
    PENDING = 'PENDING',
    ACTIVE = 'ACTIVE',
    DELETED = 'DELETED',
    EXPIRED = 'EXPIRED'
}

export interface IWhipround {
    id?: number;
    invitation?: string;
    imageUrl?: string;
    startDate?: Moment;
    endDate?: Moment;
    status?: WhiproundStatus;
    contributions?: IContribution[];
    organisers?: IPerson[];
}

export class Whipround implements IWhipround {
    constructor(
        public id?: number,
        public invitation?: string,
        public imageUrl?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public status?: WhiproundStatus,
        public contributions?: IContribution[],
        public organisers?: IPerson[]
    ) {}
}
