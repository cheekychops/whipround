import { Moment } from 'moment';
import { IWhipround } from 'app/shared/model//whipround.model';
import { IPerson } from 'app/shared/model//person.model';

export const enum Currency {
    GBP = 'GBP'
}

export const enum ContributionStatus {
    COLLECTED = 'COLLECTED',
    PENDING_APPROVAL = 'PENDING_APPROVAL',
    APPROVED = 'APPROVED'
}

export interface IContribution {
    id?: number;
    currency?: Currency;
    amount?: number;
    date?: Moment;
    fee?: number;
    status?: ContributionStatus;
    payKey?: string;
    preapprovalName?: string;
    preapprovalId?: number;
    whiprounds?: IWhipround[];
    contributors?: IPerson[];
}

export class Contribution implements IContribution {
    constructor(
        public id?: number,
        public currency?: Currency,
        public amount?: number,
        public date?: Moment,
        public fee?: number,
        public status?: ContributionStatus,
        public payKey?: string,
        public preapprovalName?: string,
        public preapprovalId?: number,
        public whiprounds?: IWhipround[],
        public contributors?: IPerson[]
    ) {}
}
