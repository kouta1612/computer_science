import { Performance } from "./types/Invoice";
import { Player } from "./types/Plays";

export default class PerformanceCalcurator {
    performance: Performance
    play: Player;

    constructor(aPerformance: Performance, aPlay: Player) {
        this.performance = aPerformance
        this.play = aPlay
    }

    get amount(): number {
        throw new Error("サブクラスの責務");
    }

    get volumeCreditsFor() {
        return Math.max(this.performance.audience - 30, 0)
    }
}
