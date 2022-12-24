import PerformanceCalculator from "./PerformanceCalculator";
import { Performance } from "./types/Invoice";
import { Player } from "./types/Plays";

export default class ComedyCalculator extends PerformanceCalculator {
    constructor(aPerformance: Performance, aPlay: Player) {
        super(aPerformance, aPlay)
    }

    get amount(): number {
        let result = 30000
        if (this.performance.audience > 20) {
            result += 10000 + 500 * (this.performance.audience - 20)
        }
        result += 300 * this.performance.audience

        return result
    }

    get volumeCreditsFor() {
        return super.volumeCreditsFor + Math.floor(this.performance.audience / 5)
    }
}
