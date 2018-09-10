export interface IPreapproval {
    id?: number;
    name?: string;
    contributionId?: number;
}

export class Preapproval implements IPreapproval {
    constructor(public id?: number, public name?: string, public contributionId?: number) {}
}
