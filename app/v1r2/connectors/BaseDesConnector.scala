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

package v1r2.connectors

import config.AppConfig
import play.api.Logger
import play.api.libs.json.Writes
import uk.gov.hmrc.http.logging.Authorization
import uk.gov.hmrc.http.{HeaderCarrier, HttpReads}
import uk.gov.hmrc.play.bootstrap.http.HttpClient

import scala.concurrent.{ExecutionContext, Future}

trait BaseDesConnector {
  val http: HttpClient
  val appConfig: AppConfig

  val logger = Logger(this.getClass)
  val CORRELATION_ID = "CorrelationId"

  private[connectors] def desHeaderCarrier(implicit hc: HeaderCarrier, correlationId: String): HeaderCarrier = {

    if(hc.headers.exists(header => header._1 == CORRELATION_ID)) {
      hc.copy(authorization = Some(Authorization(s"Bearer ${appConfig.desToken}")))
        .withExtraHeaders("Environment" -> appConfig.desEnv)
    } else{
      hc.copy(authorization = Some(Authorization(s"Bearer ${appConfig.desToken}")))
        .withExtraHeaders("Environment" -> appConfig.desEnv, CORRELATION_ID -> correlationId)
    }
  }

  def post[Body: Writes, Resp](body: Body, uri: DesUri[Resp])(implicit ec: ExecutionContext,
                                                              hc: HeaderCarrier,
                                                              httpReads: HttpReads[DesOutcome[Resp]],
                                                              correlationId: String): Future[DesOutcome[Resp]] = {

    def doPost(implicit hc: HeaderCarrier): Future[DesOutcome[Resp]] = {
      http.POST(s"${appConfig.desBaseUrl}/${uri.value}", body)
    }

    doPost(desHeaderCarrier(hc, correlationId))
  }

  def get[Resp](uri: DesUri[Resp])(implicit ec: ExecutionContext,
                                   hc: HeaderCarrier,
                                   httpReads: HttpReads[DesOutcome[Resp]],
                                   correlationId: String): Future[DesOutcome[Resp]] = {

    def doGet(implicit hc: HeaderCarrier): Future[DesOutcome[Resp]] = {
      http.GET(s"${appConfig.desBaseUrl}/${uri.value}")
    }
    doGet(desHeaderCarrier(hc, correlationId))
  }

  def get[Resp](uri: DesUri[Resp], param: Seq[(String, String)])(implicit ec: ExecutionContext,
                                   hc: HeaderCarrier,
                                   httpReads: HttpReads[DesOutcome[Resp]],
                                   correlationId: String): Future[DesOutcome[Resp]] = {

    def doGet(implicit hc: HeaderCarrier): Future[DesOutcome[Resp]] = {
      http.GET(s"${appConfig.desBaseUrl}/${uri.value}", queryParams = param)
    }

    doGet(desHeaderCarrier(hc, correlationId))
  }

}
