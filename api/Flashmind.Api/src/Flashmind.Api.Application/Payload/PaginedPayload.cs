namespace Flashmind.Api.Application.Payload
{
    public class PaginedPayload
    {
        public int Page { get; set; }

        public string Search { get; set; }

        public string Language { get; set; }
    }
}