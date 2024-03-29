/*
 * Copyright 2021 HM Revenue & Customs
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

package v1r2.models.response.getCalculation.taxableIncome.detail.payPensionsProfit.ukPropertyNonFhl.detail

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import v1r2.models.des.ReliefClaimed
import v1r2.models.domain.TypeOfClaim
import v1r2.models.request.DesTaxYear

case class UkPropertyNonFhlResultOfClaimApplied(claimId: Option[String],
                                                originatingClaimId: Option[String],
                                                taxYearClaimMade: String,
                                                claimType: TypeOfClaim,
                                                mtdLoss: Boolean,
                                                taxYearLossIncurred: String,
                                                lossAmountUsed: BigInt,
                                                remainingLossValue: BigInt)

object UkPropertyNonFhlResultOfClaimApplied {
  implicit val reads: Reads[UkPropertyNonFhlResultOfClaimApplied] = (
    (JsPath \ "claimId").readNullable[String] and
      (JsPath \ "originatingClaimId").readNullable[String] and
      (JsPath \ "taxYearClaimMade").read[Int].map(DesTaxYear.fromDesIntToString) and
      (JsPath \ "claimType").read[ReliefClaimed].map(_.toTypeOfClaim) and
      (JsPath \ "mtdLoss").read[Boolean] and
      (JsPath \ "taxYearLossIncurred").read[Int].map(DesTaxYear.fromDesIntToString) and
      (JsPath \ "lossAmountUsed").read[BigInt] and
      (JsPath \ "remainingLossValue").read[BigInt]
  )(UkPropertyNonFhlResultOfClaimApplied.apply _)

  implicit val writes: OWrites[UkPropertyNonFhlResultOfClaimApplied] = Json.writes[UkPropertyNonFhlResultOfClaimApplied]
}
