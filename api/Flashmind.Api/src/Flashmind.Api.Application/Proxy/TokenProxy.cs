using Newtonsoft.Json;

namespace Flashmind.Api.Application.Proxy
{
    public class TokenProxy
    {
        [JsonProperty("access_token")]
        public string AccessToken { get; set; }

        [JsonProperty("expires_in")]
        public long ExpiresIn { get; set; }
    }
}