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

package v1r2.controllers.requestParsers.validators

import config.FixedConfig
import v1r2.controllers.requestParsers.validators.validations.{JsonFormatValidation, MinTaxYearValidation, NinoValidation, TaxYearValidation}
import v1r2.models.errors.{MtdError, RuleIncorrectOrEmptyBodyError}
import v1r2.models.request.triggerCalculation.{TriggerTaxCalculation, TriggerTaxCalculationRawData}

class TriggerTaxCalculationValidator extends Validator[TriggerTaxCalculationRawData] with FixedConfig {

  private val validationSet = List(parameterFormatValidation, taxYearValidation, mtdTaxYearValidation)

  private def parameterFormatValidation: TriggerTaxCalculationRawData => List[List[MtdError]] = { data =>
    List(
      NinoValidation.validate(data.nino),
      JsonFormatValidation.validate[TriggerTaxCalculation](data.body.json, RuleIncorrectOrEmptyBodyError)

    )
  }

  private def taxYearValidation: TriggerTaxCalculationRawData => List[List[MtdError]] = { data =>
    val req = data.body.json.as[TriggerTaxCalculation]
    List(
      TaxYearValidation.validate(req.taxYear)
    )
  }

  private def mtdTaxYearValidation: TriggerTaxCalculationRawData => List[List[MtdError]] = { data =>
    val req = data.body.json.as[TriggerTaxCalculation]
    List(
      MinTaxYearValidation.validate(req.taxYear, 2018)
    )
  }

  override def validate(data: TriggerTaxCalculationRawData): List[MtdError] = {
    run(validationSet, data).distinct
  }
}
