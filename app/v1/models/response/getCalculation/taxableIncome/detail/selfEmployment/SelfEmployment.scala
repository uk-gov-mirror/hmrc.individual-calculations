/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package v1.models.response.getCalculation.taxableIncome.detail.selfEmployment

import play.api.libs.functional.syntax._
import play.api.libs.json._
import utils.NestedJsonReads
import v1.models.response.getCalculation.taxableIncome.detail.selfEmployment.detail.LossClaimsDetail
import v1.models.response.getCalculation.taxableIncome.detail.selfEmployment.summary.LossClaimsSummary

case class SelfEmployment(
    selfEmploymentId: String,
    totalIncome: Option[BigDecimal],
    totalExpenses: Option[BigDecimal],
    netProfit: Option[BigDecimal],
    netLoss: Option[BigDecimal],
    class4Loss: Option[BigInt],
    totalAdditions: Option[BigDecimal],
    totalDeductions: Option[BigDecimal],
    accountingAdjustments: Option[BigDecimal],
    adjustedIncomeTaxLoss: Option[BigInt],
    taxableProfit: Option[BigDecimal],
    taxableProfitAfterIncomeTaxLossesDeduction: Option[BigInt],
    lossClaimsSummary: Option[LossClaimsSummary],
    lossClaimsDetail: Option[LossClaimsDetail]
)

object SelfEmployment extends NestedJsonReads {

  implicit val writes: OWrites[SelfEmployment] = Json.writes[SelfEmployment]

  private implicit val singleReads: Reads[SelfEmployment] = ((JsPath \ "incomeSourceId").read[String] and
    (JsPath \ "totalIncome").readNullable[BigDecimal] and
    (JsPath \ "totalExpenses").readNullable[BigDecimal] and
    (JsPath \ "netProfit").readNullable[BigDecimal] and
    (JsPath \ "netLoss").readNullable[BigDecimal] and
    (JsPath \ "class4Loss").readNullable[BigInt] and
    (JsPath \ "totalAdditions").readNullable[BigDecimal] and
    (JsPath \ "totalDeductions").readNullable[BigDecimal] and
    (JsPath \ "accountingAdjustments").readNullable[BigDecimal] and
    (JsPath \ "adjustedIncomeTaxLoss").readNullable[BigInt] and
    (JsPath \ "taxableProfit").readNullable[BigDecimal] and
    (JsPath \ "taxableProfitAfterIncomeTaxLossesDeduction").readNullable[BigInt] and
    JsPath.readNullable[LossClaimsSummary].map(_.getOrElse(LossClaimsSummary.emptyLossClaimsSummary)).map{
      case LossClaimsSummary.emptyLossClaimsSummary => None
      case lossClaimSummary: LossClaimsSummary => Some(lossClaimSummary)
    } and Reads.pure(None))(SelfEmployment.apply _)

  implicit val seqReads: Reads[Seq[SelfEmployment]] = ((JsPath \ "calculation" \ "businessProfitAndLoss")
    .readNestedNullable[Seq[JsValue]]
    .map(_.getOrElse(Seq.empty).flatMap(js => LossClaimsDetail.filterByIncomeSourceType(js).asOpt[SelfEmployment]))
    .map(x => Some(x)) and
    JsPath.readNullable[LossClaimsDetail])(buildSelfEmployments(_, _))

  def buildSelfEmployments(seqSelfEmploysOpt: Option[Seq[SelfEmployment]], detailsOpt: Option[LossClaimsDetail]): Seq[SelfEmployment] = {
    val seqSelfEmploys    = seqSelfEmploysOpt.getOrElse(Seq.empty)
    val details = detailsOpt.getOrElse(LossClaimsDetail.emptyLossClaimsDetail)
    seqSelfEmploys.map(selfEmploy => selfEmploy.copy(lossClaimsDetail = details.filterById(selfEmploy.selfEmploymentId)))
  }

}