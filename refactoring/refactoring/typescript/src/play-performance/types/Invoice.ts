import { Plays } from './Plays'

interface Invoice {
    customer: string,
    performances: Performance[]
}

interface Performance {
    playID: keyof Plays,
    audience: number
}

export { Invoice, Performance }
