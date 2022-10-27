import { Plays } from "./types/Plays"
import { Invoice } from './types/Invoice'

import invoices from "./json/invoices.json"
import plays from "./json/plays.json"

import createStatementData from './createStatementData';

// データ検証用
const invoice = invoices[0] as Invoice
console.log(statement(invoice, plays))
console.log(htmlStatement(invoice, plays))

/**
 * 請求書データを平文として返却
 *
 * @param invoice
 * @param plays
 * @returns string
 */
function statement (invoice: Invoice, plays: Plays): string {
    return renderPlainText(createStatementData(invoice, plays))
}

/**
 * 請求書データをHTMLとして返却
 *
 * @param invoice
 * @param plays
 * @returns string
 */
function htmlStatement(invoice: Invoice, plays: Plays): string {
    return renderHtml(createStatementData(invoice, plays))
}

/**
 * 平文テキストを出力
 *
 * @param data
 * @return string
 */
function renderPlainText(data: any): string {
    let result = `Statement for ${data.customer}\n`

    for (let perf of data.performances) {
        result += ` ${perf.play.name}: ${usd(perf.amount)} (${perf.audience} seats)\n`
    }

    result += `Amount owed is ${usd(data.totalAmount)}\n`
    result += `You earned ${data.totalVolumeCredits} credits\n`
    return result
}

/**
 * HTMLテキストを出力
 *
 * @param data
 * @return string
 */
 function renderHtml (data: any) {
    let result = `<h1>Statement for ${data.customer}</h1>\n`;
    result += "<table>\n";
    result += "<tr><th>play</th><th>seats</th><th>cost</th></tr>";
    for (let perf of data.performances) {
        result += ` <tr><td>${perf.play.name}</td><td>${perf.audience}</td>`;
        result += `<td>${usd(perf.amount)}</td></tr>\n`;
    }
    result += "</table>\n";
    result += `<p>Amount owed is <em>${usd(data.totalAmount)}</em></p>\n`;
    result += `<p>You earned <em>${data.totalVolumeCredits}</em> credits</p>\n`;
    return result;
}

/**
 * USDフォーマットに変換して返却
 *
 * @param aNumber
 * @returns string
 */
function usd(aNumber: number): string {
    return new Intl.NumberFormat(
        "en-US",
        { style: "currency", currency: "USD", minimumFractionDigits: 2 }
    ).format(aNumber/100)
}
