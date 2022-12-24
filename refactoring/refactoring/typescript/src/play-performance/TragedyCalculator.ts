import PerformanceCalculator from "./PerformanceCalculator";
import { Performance } from "./types/Invoice";
import { Player } from "./types/Plays";

export default class TragedyCalculator extends PerformanceCalculator {
    constructor(aPerformance: Performance, aPlay: Player) {
        super(aPerformance, aPlay)
    }

    get amount(): number {
        let result = 40000
        if (this.performance.audience > 30) {
            result += 1000 * (this.performance.audience - 30)
        }

        return result
    }
}
