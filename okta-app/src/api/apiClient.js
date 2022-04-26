import axios from "axios"

export default class ApiClient {

  // @TODO DELETE - Training Function
  async request(
    { 
      baseURL = "https://api.publicapis.org", 
      url = "/entries", 
      method = "GET", 
      data = {}, 
      params = {}
    }
  )
  {
    console.log(data)
    const config = { baseURL, url, method, params }
   const request =  await axios.request(config)
   return request.data
  }
  // END of Training Function


  /**
   * Api Function
   * @param accessToken  - Required parameter: Token type: Bearer
   * @param url          - Required parameter: Example: /IncomeExpenses
   * @param method       - Optional - default: GET
   * @param params       - Optional - default: {}
   * @param data         - Optional - default: {}
   * @param baseURL      - Optional - default: https://mois-endor-api-gateway.herokuapp.com
  */
  async getMyApi(
    {
      accessToken, 
      method = "GET", 
      url="/IncomeExpenses", 
      params = {}, 
      data = {}, 
      baseURL = "https://mois-endor-api-gateway.herokuapp.com"
    }
  )
  {   
    console.log(`Authorization: Bearer ${accessToken}`);
    const config = { baseURL, headers: {'Authorization': 'Bearer ' + accessToken }, url, method, data, params}
    const request =  await axios.request(config)
    console.log(request)
    return request.data
  }
  // END of Api Function


}