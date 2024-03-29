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

package v1r2.models.response.getCalculation.taxableIncome.detail.payPensionsProfit

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import utils.NestedJsonReads
import v1r2.models.response.getCalculation.taxableIncome.detail.payPensionsProfit.eeaPropertyFhl.EeaPropertyFhl
import v1r2.models.response.getCalculation.taxableIncome.detail.payPensionsProfit.foreignProperty.ForeignProperty
import v1r2.models.response.getCalculation.taxableIncome.detail.payPensionsProfit.selfEmployment.SelfEmployment
import v1r2.models.response.getCalculation.taxableIncome.detail.payPensionsProfit.ukPropertyFhl.UkPropertyFhl
import v1r2.models.response.getCalculation.taxableIncome.detail.payPensionsProfit.ukPropertyNonFhl.UkPropertyNonFhl

case class BusinessProfitAndLoss(selfEmployments: Option[Seq[SelfEmployment]],
                                 ukPropertyFhl: Option[UkPropertyFhl],
                                 ukPropertyNonFhl: Option[UkPropertyNonFhl],
                                 eeaPropertyFhl: Option[EeaPropertyFhl],
                                 foreignProperty: Option[ForeignProperty])

object BusinessProfitAndLoss extends NestedJsonReads {
  val empty: BusinessProfitAndLoss = BusinessProfitAndLoss(None, None, None, None, None)

  implicit val reads: Reads[BusinessProfitAndLoss] = (
    JsPath.readNullable[Seq[SelfEmployment]](SelfEmployment.seqReads).mapEmptySeqToNone and
      JsPath.readNullable[UkPropertyFhl].mapEmptyModelToNone(UkPropertyFhl.empty) and
      JsPath.readNullable[UkPropertyNonFhl].mapEmptyModelToNone(UkPropertyNonFhl.empty) and
      JsPath.readNullable[EeaPropertyFhl].mapEmptyModelToNone(EeaPropertyFhl.empty) and
      JsPath.readNullable[ForeignProperty].mapEmptyModelToNone(ForeignProperty.empty)
  )(BusinessProfitAndLoss.apply _)

  implicit val writes: Writes[BusinessProfitAndLoss] = Json.writes[BusinessProfitAndLoss]
}
