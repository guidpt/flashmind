using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using Flashmind.Api.Application.Api;
using Flashmind.Api.Application.Payload;
using Flashmind.Api.Application.Proxy;
using Flashmind.Api.Deck.Repository;
using Flashmind.Data.Entity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.Documents;
using Microsoft.Extensions.Configuration;
using RestEase;

namespace Flashmind.Api.Deck.Controllers
{
    [Route("api/[controller]")]
    public class DeckController : Controller
    {
        private readonly IConfiguration _configuration;
        private DeckRepository _repository;

        public DeckController(DeckRepository repository, IConfiguration configuration)
        {
            _repository = repository;
            _configuration = configuration;
        }

        public async Task<IActionResult> Get([FromHeader] string authorization, PaginedPayload payload)
        {
            try
            {
                var userObj = await GetUserByToken(authorization);

                var decks = _repository.Where(w => !w.IsBan);

                if (!string.IsNullOrEmpty(payload.Search))
                {
                    decks = decks.Where(w => w.Name.Contains(payload.Search) || w.Tags.Contains(payload.Search));
                }

                if (!string.IsNullOrEmpty(payload.Language))
                {
                    decks = decks.Where(w => w.Language == payload.Language);
                }

                decks = decks.Where(w => w.UserId == userObj.Id).OrderBy(o => o.CreatedDate);

                var decksObjs = decks.Skip(payload.Page * 10)
                    .Take(10)
                    .AsEnumerable()
                    .Select(Map<DeckEntity, DeckProxy>);

                foreach (var item in decksObjs)
                {
                    var user = await GetUserById(authorization, item.UserId);

                    item.UserName = user.Name;
                    item.ListTags = item.Tags.Split('|');
                }

                return Json(decksObjs);

            }
            catch (Exception e)
            {
                return BadRequest(new ApiErrorProxy()
                {
                    Code = 99,
                    Message =  e.Message
                });
            }
        }

        [HttpGet("recomended")]
        public async Task<IActionResult> GetShared([FromHeader] string authorization, PaginedPayload payload)
        {
            try
            {
                var userObj = await GetUserByToken(authorization);

                var decks = _repository.Where(w => !w.IsBan);

                if (!string.IsNullOrEmpty(payload.Search))
                {
                    decks = decks.Where(w => w.Name.Contains(payload.Search) || w.Tags.Contains(payload.Search));
                }

                if (!string.IsNullOrEmpty(payload.Language))
                {
                    decks = decks.Where(w => w.Language == payload.Language);
                }

                decks = decks.Where(w => w.Privacy == PrivacyEnum.Public).OrderBy(o => o.DownloadCount);

                var decksObjs = decks.Skip(payload.Page * 10)
                    .Take(10)
                    .AsEnumerable()
                    .Select(Map<DeckEntity, DeckProxy>);

                foreach (var item in decksObjs)
                {
                    var user = await GetUserById(authorization, item.UserId);

                    item.UserName = user.Name;
                    item.ListTags = item.Tags.Split('|');
                }

                return Json(decksObjs);

            }
            catch (Exception e)
            {
                return BadRequest(new ApiErrorProxy()
                {
                    Code = 99,
                    Message =  e.Message
                });
            }
        }

        [HttpGet("recently")]
        public async Task<IActionResult> GetRecently([FromHeader] string authorization, PaginedPayload payload)
        {
            try
            {
                var userObj = await GetUserByToken(authorization);

                var decks = _repository.Where(w => !w.IsBan);

                if (!string.IsNullOrEmpty(payload.Search))
                {
                    decks = decks.Where(w => w.Name.Contains(payload.Search) || w.Tags.Contains(payload.Search));
                }

                if (!string.IsNullOrEmpty(payload.Language))
                {
                    decks = decks.Where(w => w.Language == payload.Language);
                }
                
                decks = decks.Where(w => w.Privacy == PrivacyEnum.Public)
                    .OrderBy(o => o.DownloadCount).ThenBy(t => t.LastUpdateDate);

                var decksObjs = decks.Skip(payload.Page * 10)
                    .Take(10)
                    .AsEnumerable()
                    .Select(Map<DeckEntity, DeckProxy>);

                foreach (var item in decksObjs)
                {
                    var user = await GetUserById(authorization, item.UserId);

                    item.UserName = user.Name;
                    item.ListTags = item.Tags.Split('|');
                }

                return Json(decksObjs);

            }
            catch (Exception e)
            {
                return BadRequest(new ApiErrorProxy()
                {
                    Code = 99,
                    Message =  e.Message
                });
            }
        }



        private OrderTo Map<Order, OrderTo>(Order item)
        {
            var config = new MapperConfiguration(cfg => cfg.CreateMap<Order, OrderTo>());
            var mapper = config.CreateMapper();
            return mapper.Map<OrderTo>(item);
        }

        private async Task<UserProxy> GetUserByToken(string authorization)
        {
            var api = RestClient.For<IUser>(_configuration["Login:Url"]);
            return (await api.GetMeAsync(authorization));
        }

        private async Task<UserEntity> GetUserById(string authorization, Guid id)
        {
            var api = RestClient.For<IUser>(_configuration["Login:Url"]);
            var userProxy = await api.GetUserAsync(authorization, id.ToString());

            var user = Map<UserProxy, UserEntity>(userProxy);

            return user;
        }
    }
}
