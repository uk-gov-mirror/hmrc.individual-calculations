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

package v2.fixtures.getCalculation.incomeTaxAndNics.detail

import play.api.libs.json.{JsValue, Json}
import v2.models.response.getCalculation.incomeTaxAndNics.detail.TaxDeductedAtSource

object TaxDeductedAtSourceFixtures {

  val taxDeductedAtSourceJson: JsValue = Json.parse(
    """
      |{
      | "ukLandAndProperty" : 100,
      | "bbsi" : 200,
      | "cis": 110.25,
      | "securities": 120.35,
      | "voidedIsa": 130.45,
      | "payeEmployments": 140.55,
      | "occupationalPensions": 150.65,
      | "stateBenefits": 160.75,
      | "specialWithholdingTaxOrUkTaxPaid": 170.50
      |}
    """.stripMargin)

  val taxDeductedAtSourceOutputJson: JsValue = Json.parse(
    """
      |{
      | "ukLandAndProperty" : 100,
      | "savings" : 200,
      | "cis": 110.25,
      | "securities": 120.35,
      | "voidedIsa": 130.45,
      | "payeEmployments": 140.55,
      | "occupationalPensions": 150.65,
      | "stateBenefits": 160.75,
      | "specialWithholdingTaxOrUkTaxPaid": 170.50
      |}
    """.stripMargin)

  val taxDeductedAtSourceModel =
    TaxDeductedAtSource(
      Some(100), Some(200), Some(110.25), Some(120.35), Some(130.45), Some(140.55), Some(150.65), Some(160.75), Some(170.50)
    )
}