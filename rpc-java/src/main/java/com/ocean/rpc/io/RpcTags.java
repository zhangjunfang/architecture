package com.ocean.rpc.io;

public interface RpcTags {
	/* Serialize Tags */
	int TagInteger = 'i';
	int TagLong = 'l';
	int TagDouble = 'd';
	int TagNull = 'n';
	int TagEmpty = 'e';
	int TagTrue = 't';
	int TagFalse = 'f';
	int TagNaN = 'N';
	int TagInfinity = 'I';
	int TagDate = 'D';
	int TagTime = 'T';
	int TagUTC = 'Z';
	int TagBytes = 'b';
	int TagUTF8Char = 'u';
	int TagString = 's';
	int TagGuid = 'g';
	int TagList = 'a';
	int TagMap = 'm';
	int TagClass = 'c';
	int TagObject = 'o';
	int TagRef = 'r';
	/* Serialize Marks */
	int TagPos = '+';
	int TagNeg = '-';
	int TagSemicolon = ';';
	int TagOpenbrace = '{';
	int TagClosebrace = '}';
	int TagQuote = '"';
	int TagPoint = '.';
	/* Protocol Tags */
	int TagFunctions = 'F';
	int TagCall = 'C';
	int TagResult = 'R';
	int TagArgument = 'A';
	int TagError = 'E';
	int TagEnd = 'z';
}