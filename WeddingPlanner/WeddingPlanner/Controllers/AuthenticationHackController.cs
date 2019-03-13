using System;
using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using WeddingPlanner.DAL;
using WeddingPlanner.WebApp.Authentication;
using WeddingPlanner.WebApp.Models.AccountViewModels;
using WeddingPlanner.WebApp.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.Options;
using System.Security.Claims;


namespace WeddingPlanner.WebApp.Controllers
{
    [Route( "/[controller]" )]
    public class AuthenticationHackController : Controller
    {
        readonly UserGateway _userGateway;
        readonly UserService _userService;
        readonly TokenService _tokenService;
        readonly IAuthenticationSchemeProvider _authenticationSchemeProvider;  
        readonly Random _random;
        readonly IOptions<SpaOptions> _spaOptions;

        public AuthenticationHackController( UserGateway userGateway, UserService userService, TokenService tokenService, IAuthenticationSchemeProvider authenticationSchemeProvider, IOptions<SpaOptions> spaOptions )
        {
            _userGateway = userGateway;
            _userService = userService;
            _tokenService = tokenService;
            _authenticationSchemeProvider = authenticationSchemeProvider;
            _spaOptions = spaOptions;
            _random = new Random();
        }

        [HttpGet]
        [AllowAnonymous]
        public IActionResult Login()
        {
            Token token = _tokenService.GenerateToken( "4", "s@s.com" );
            return Ok( new { Success = true, Bearer = token, Email = "s@s.com" } );
        }

    }
}
