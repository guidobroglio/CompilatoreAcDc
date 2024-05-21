package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken 
{

    @Test
    void testToString() 
    {
        // Test per TokenType.INT
        Token token1 = new Token(TokenType.INT, 3, ";");
        assertEquals(TokenType.INT, token1.getTipo());
        assertEquals(3, token1.getRiga());
        assertEquals(";", token1.getVal());
        assertEquals("<INT,r:3,;>", token1.toString());

        // Test per TokenType.FLOAT
        Token token2 = new Token(TokenType.FLOAT, 5);
        assertEquals(TokenType.FLOAT, token2.getTipo());
        assertEquals(5, token2.getRiga());
        assertNull(token2.getVal());
        assertEquals("<FLOAT,r:5>", token2.toString());
    }

}
