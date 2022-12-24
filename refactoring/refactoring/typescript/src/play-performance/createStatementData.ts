import { Plays, Player } from "./types/Plays"
import { Invoice, Performance } from './types/Invoice'
import PerformanceCalculator from "./PerformanceCalculator"
import TragedyCalculator from "./TragedyCalculator"
import ComedyCalculator from "./ComedyCalculator"

/**
 * statementオブジェクトを作成
 *
 * @param invoice
 * @param plays
 * @returns
 */
export default function createStatementData(invoice: Invoice, plays: Plays) {
    const result: any = {}
    result.customer = invoice.customer
    result.performances = invoice.performances.map(enrichPerformance)
    result.totalAmount = totalAmount(result)
    result.totalVolumeCredits = totalVolumeCredits(result)
    return result

    function enrichPerformance(aPerformance: Performance) {
        const calculator = createPerformanceCalculator(aPerformance, playFor(aPerformance))
        const result: any = Object.assign(aPerformance, {})
        result.play = calculator.play
        result.amount = calculator.amount
        result.volumeCredits = calculator.volumeCreditsFor
        return result
    }

    function createPerformanceCalculator(aPerformance: Performance, aPlay: Player) {
        switch (aPlay.type) {
            case "tragedy": return new TragedyCalculator(aPerformance, aPlay)
            case "comedy" : return new ComedyCalculator(aPerformance, aPlay)
            default: throw new Error(`未知の演劇の種類：${aPlay.type}`);
        }
    }

    function totalAmount(data: any): number {
        return data.performances
            .reduce((total: number, perf: any) => total + perf.amount, 0);
    }

    function totalVolumeCredits(data: any): number {
        return data.performances
            .reduce((total: number, perf: any) => total + perf.volumeCredits, 0)
    }

    function playFor(aPerformance: Performance): Player {
        return plays[aPerformance.playID]
    }
}
