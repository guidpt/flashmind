using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using Flashmind.Api.Application.Payload;
using Flashmind.Api.Application.Proxy;
using Flashmind.Api.Application.Repository;
using Flashmind.Api.Token;
using Flashmind.Api.Token.Repository;
using Flashmind.Data.Entity;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.Documents.SystemFunctions;

namespace Flashmind.Api.Authenticate.Controllers
{
    [Route("api/[controller]")]
    public class AuthController : Controller
    {

        private UserRepository _repository;

        public AuthController(UserRepository repository)
        {
            _repository = repository;
        }

        [HttpPost]
        [AllowAnonymous]
        public async Task<IActionResult> Post([FromBody] CreateUser createUser)
        {
            try
            {
                var item = Map<CreateUser, UserEntity>(createUser);
                item.Id = Guid.NewGuid();

                item.Password = AuthenticationHelper.HashPasswordSalted(item.Password);

                if(string.IsNullOrEmpty(item.Name))
                {
                    item.IsRegistred = false;
                }

                await _repository.AddAndSaveAsync(item);

                return Json(Map<UserEntity, UserProxy>(item));
            }
            catch (Exception e)
            {
                return BadRequest(new ApiErrorProxy
                {
                    Code = 0,
                    Message = e.Message
                });
            }
        }

        private OrderTo Map<Order, OrderTo>(Order item)
        {
            var config = new MapperConfiguration(cfg => cfg.CreateMap<Order, OrderTo>());
            var mapper = config.CreateMapper();
            return mapper.Map<OrderTo>(item);
        }

    }
}