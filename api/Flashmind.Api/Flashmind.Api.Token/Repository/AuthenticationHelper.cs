using System;
using System.Security.Cryptography;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;

namespace Flashmind.Api.Token
{
    public class AuthenticationHelper
    {
        public static string HashPasswordSalted(string password)
        {
            var salt = GetSalt();

            // derive a 256-bit subkey (use HMACSHA1 with 10,000 iterations)
            var hashed = Convert.ToBase64String(KeyDerivation.Pbkdf2(password, salt, KeyDerivationPrf.HMACSHA1, 10000, 256 / 8));
            var saltString = BitConverter.ToString(salt).Replace("-", "").ToLower();

            return saltString + hashed;
        }

        public static bool VerifyPasswordHash(string password, string saltedPassword)
        {
            try
            {
                var saltString = string.Join("-", saltedPassword.Substring(0, 32).SplitInParts(2));
                var hash = saltedPassword.Substring(32, saltedPassword.Length - 32);

                // derive a 256-bit subkey (use HMACSHA1 with 10,000 iterations)
                var hashed = Convert.ToBase64String(KeyDerivation.Pbkdf2(password, saltString.GetBytesFromBitConverter(), KeyDerivationPrf.HMACSHA1, 10000, 256 / 8));

                return hash == hashed;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }

            return false;
        }

        private static byte[] GetSalt()
        {
            var bytes = new byte[128 / 8];

            using (var keyGenerator = RandomNumberGenerator.Create())
            {
                keyGenerator.GetBytes(bytes);
            }


            return bytes;
        }
    }
}