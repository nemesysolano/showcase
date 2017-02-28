package com.souschef.json.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 *<p>This class implements a JSON parser</p>
 *<p>It only performs the JSON content parsing and validation, further processing must be implemented in <code>eventListener</code> member.</p>
  *<p>Copyright &copy; 1998 - 2010 <a href="http://www.castlebreck.com">Castlebreck Inc.</a>  All rights reserved.  This software may only be copied, altered, transferred or used in accordance<br/>
 *with the Castlebreck General Services Agreement which is available upon request from Castlebreck Inc. Visit www.castlebreck.com for<br/>
 *contact information.  This notice may not be removed and must be included with any copies of this work.</p>
 *@author rsolano
 */
public class Parser implements ParserEventListener{
	
	/**
 *<p>Empty string constant</p>
	 */
	public static final String EMPTY_STRING = "";
	
	/**
 *<p>Regular expression that validates scientific notation numbers.</p>
 *<p>Strings matching this regexp (<b>^-?\d+\.\d+(e|E)(\+|-)\d+</b>) are converted to <code>java.lang.Double</code><p>
	 */
	public static final Pattern SCIENTIFIC_NOTATION_PATTERN = Pattern.compile("^-?\\d+\\.\\d+(e|E)(\\+|-)\\d+$");
	
	/**
 *<p>Regular expression that validates fixed decimal numbers.</p>
 *<p>Strings matching this regexp (<b>-?\d+\.\d+$</b>) are converted to <code>java.math.BigDecimal</code></p>
	 */
	public static final Pattern FIXED_DECIMAL_PATTERN = Pattern.compile("^-?\\d+\\.\\d+$");
	
	/**
 *<p>Regular expression that validates integer numbers.</p>
 *<p>Strings matching this regexp (<b>-?\d+$</b>) are converted to <code>java.lang.Long</code></p>
	 */	
	public static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+$");

	/**
 *<p>Short date (<b>yyyy-MM-dd</b>) formatter </p>
	 */
	public static SimpleDateFormat SHORT_DATE_FORMATTER = new  SimpleDateFormat("yyyy-MM-dd");
	
	/**
 *<p>Long date (<b>yyyy-MM-dd hh:mm:ssa</b>) formatter.</p>
	 */
	public static SimpleDateFormat LONG_DATE_PARSER_AM_PM = new  SimpleDateFormat("yyyy-MM-dd hh:mm:ssa");
	
	/**
 *<p>Long date (<b>yyyy-MM-dd kk:mm:ss</b>) formatter.</p>
	 */
	public static SimpleDateFormat  LONG_DATE_PARSER_24H = new  SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

	/**
 *<p>Regular expression that validates short dates.</p>
 *<p>Strings matching this regexp (<b>^\d{4}-\d{2}-\d{2}$</b>) are converted to <code>java.util.Date</code></p>
	 */	
	public static Pattern SHORT_DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
	
	/**
 *<p>Regular expression that validates long dates with AM|PM suffix.</p>
 *<p>Strings matching this regexp (<b>^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}(AM|PM|am|pm)$</b>) are converted to <code>java.util.Date</code></p>
	 */		
	public static Pattern LONG_DATE_PATTERN_AM_PM = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(AM|PM|am|pm)$");
	
	/**
 *<p>Regular expression that validates long dates without AM|PM suffix.</p>
 *<p>Strings matching this regexp (<b>^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$</b>) are converted to <code>java.util.Date</code></p>
	 */
	public static Pattern LONG_DATE_PATTERN_24H = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
	
	/**
 *<p>Event listener that provides specific processing to each JSON data type/struture.</p>
	 * @see com.souschef.json.parser.ParserEventListener
	 */
	private ParserEventListener eventListener;
	
	class ScanStop{
		char stop;
		ScanStop(char stop){
			this.stop = stop;
		}
		ScanStop(){
		}

		
		boolean matches(char ch) {return ch == stop;};		
	}

	ScanStop onDoubleQuoteStop = new ScanStop('"');
	ScanStop onNonJavaIdentifierCharStop = new ScanStop() {
		boolean matches(char ch) {
			return !Character.isJavaIdentifierPart(ch);
		};
	};
	
	/**
 *<p>This constructor stores the received parameter into <code>eventListener</code>.</p>
	 * @param eventListener A non null reference to a <code>ParserEventListener</code> object.
	 * @throws IllegalArgumentException If <code>eventListener</code> is null.
	 * @see com.souschef.json.parser.ParserEventListener
	 */
	public Parser(ParserEventListener eventListener) throws IllegalArgumentException{
		if(eventListener == null){
			throw new IllegalArgumentException("eventListener is null");
		}
		
		this.eventListener = eventListener;
	}
	
	
	public void onBoolean(ParserContext context, String name, boolean value)
			throws ParseException {
		this.eventListener.onBoolean(context, name, value);
	}

	
	public void onNull(ParserContext context, String name)
			throws ParseException {
		this.eventListener.onNull(context, name);
	}

	
	public void onNumber(ParserContext context, String name, Number number)
			throws ParseException {
		this.eventListener.onNumber(context, name, number);
	}

	
	public void onString(ParserContext context, String name, String string)
			throws ParseException {
		this.eventListener.onString(context, name, string);
	}

	
	public void onStructureEnd(ParserContext context, String name,
			StructureTypeEnum type) throws ParseException {
		this.eventListener.onStructureEnd(context, name, type);
	}

	
	public void onStructureStart(ParserContext context, String name,
			StructureTypeEnum type) throws ParseException {
		this.eventListener.onStructureStart(context, name, type);
	}

	
	public void onDate(ParserContext context, String name, Date value) throws ParseException {
		this.eventListener.onDate(context, name, value);
	}
	
	/**
 *<p>Reads a character from <code>reader</code>.</p>
	 * @param context Context information.
	 * @param content Reader
	 * @return Last character read or -1 if eof is reached.
	 * @throws IOException 
	 */
	private int readChar(ParserContext context, Reader content) throws IOException{
		int character = content.read();
		context.setCharacter(character);
		if(character == -1)
			context.setEof(true);
		return context.getCharacter();
	}
	
	/**
 *<p>Skips all space characters.</p>
	 * @param context Context information.
	 * @param content Reader.
	 * @return The count of space characters skipped.
	 * @throws IOException .-
	 */
	private int skipSpaces(ParserContext context, Reader content) throws IOException{
		int ch = context.getCharacter();
		while(!context.isEof() && Character.isWhitespace(ch)){			
			ch = readChar(context, content);
		}
		return ch;
	}
	
	/**
 *<p>Parses a <code>reader</code> that encodes a JSON object.</p>
	 * @param content parsing context.
	 * @param content A reader object that encodes a JSON object.
	 * @throws ParseException .-
	 * @throws IOException .-
	 * @return The same parsing context received as parameter.
	 */
	public ParserContext parse(ParserContext context, Reader content) throws IOException, ParseException{
		readChar(context, content);
		skipSpaces(context, content);
		if( context.getCharacter() == '{'){
			parseObject(context, content, null);
		}
		else
			throw new ParseException("Invalid object notation, JSON content must start with '{', current character is '"+(char)context.getCharacter(),-1);
		return context;
	}
	
	/**
 *<p>Parses a JSON object.</p>
	 * @param content parsing context.
	 * @param content A reader object that encodes a JSON object.
	 * @throws ParseException .-
	 * @throws IOException .-
	 */
	private void parseObject(ParserContext context, Reader content, String name) throws IOException, ParseException {
		char ch;
		StringBuilder buffer = new StringBuilder();
		String objectName = name;
		
		readChar(context, content);
		onStructureStart(context, name, StructureTypeEnum.MAP_TYPE);
		skipSpaces(context, content);
		
		if(context.getCharacter()!='}'){				
			ch = (char) context.getCharacter();
			while(!context.isEof() && context.getCharacter()!='}'){					
				name = parseName(context, content, buffer, ch);				
				ch = (char) context.getCharacter();
				
				switch(ch){
					case '!':
						parseCDATA(context, content, name, buffer);
						break;
						
					case '"':
						parseString(context, content, name, buffer);
						break;
						
					case 't':
					case 'f':
						parseBoolean(context, content, name, buffer);
						break;
						
					case 'n':
						parseNull(context, content, name, buffer);
						break;
					
					case '{':
						parseObject(context, content ,name);
						break;
						
					case '[':
						parseArray(context, content, name);
						break;
					default:
						if(Character.isDigit(ch)){
							parseNumber(context, content, name, buffer);
						}else{
							throw new ParseException("Invalid object notation, not a valid JSON value",-1);
						}
				}
				
				if((ch  = (char)context.getCharacter()) == ','){
					readChar(context, content);
					ch = (char) skipSpaces(context, content);
					if(context.getCharacter() == '}')
						throw new ParseException("Invalid object notation, expected content after ','",-1);
				}
			}
		}
		
		if(context.getCharacter() != '}'){
			throw new ParseException("Invalid object notation, JSON content must end with '}'",-1);
		}
		
		readChar(context, content);
		skipSpaces(context, content);		
		onStructureEnd(context, objectName, StructureTypeEnum.MAP_TYPE);
	}

	/**
 *<p>Tries to parse the next identifier in content.</p>
	 * @param context content parsing context.
	 * @param content  A reader object that encodes a JSON object.
	 * @param buffer  Auxiliary buffer
	 * @param ch Last character read.
	 * @return The token just read.
	 * @throws ParseException If the read token is not a character string.
	 * @throws IOException .-
	 */
	private String parseName(ParserContext context, Reader content, StringBuilder buffer, char ch) throws ParseException, IOException {
		ScanStop scanStop;
		
		buffer.delete(0, buffer.length());						
		scanStop = Character.isJavaIdentifierStart(ch) ? onNonJavaIdentifierCharStop : onDoubleQuoteStop;
		
		if(Character.isJavaIdentifierStart(ch)){
			scanStop = onNonJavaIdentifierCharStop;
			buffer.append(ch);
		}else {
			scanStop = onDoubleQuoteStop;
		}
		
		ch = (char) readChar(context, content);
		
		while(!context.isEof() && !scanStop.matches(ch)){
			buffer.append(ch);
			ch = (char) readChar(context, content);
		}
				
		if(context.isEof())
			throw new ParseException("Invalid object notation, unexpected end of file",-1);
		
		if(scanStop == onDoubleQuoteStop){
			ch = (char) readChar(context, content);
		}
		
		ch = (char) skipSpaces(context, content);
		
		if(ch != ':')
			throw new ParseException("Invalid object notation, identifier is not sufixed by ':'",-1);
		
		readChar(context, content);
		skipSpaces(context, content);
		
		return buffer.toString();
	}

	/**
 *<p>Tries to parse the next JSON array in content.</p>
	 * @param context content parsing context.
	 * @param content A reader object that encodes a JSON object.
	 * @param name Array name, may be null if nested within another array.
	 * @throws IOException .-
	 * @throws ParseException .-
	 */
	private void parseArray(ParserContext context, Reader content, String name) throws IOException, ParseException {
		char ch;
		StringBuilder buffer = new StringBuilder();
		String objectName = name;
		
		readChar(context, content);
		onStructureStart(context, name, StructureTypeEnum.ARRAY_TYPE);
		ch = (char) skipSpaces(context, content);
		
		if(context.getCharacter()!=']'){				
			while(!context.isEof() && context.getCharacter()!=']'){										
				
				switch(ch){
					case '!':
						parseCDATA(context, content, null, buffer);
						break;
				
					case '"':
						parseString(context, content, null, buffer);
						break;
						
					case 't':
					case 'f':
						parseBoolean(context, content, null, buffer);
						break;
						
					case 'n':
						parseNull(context, content, null, buffer);
						break;
					
					case '{':
						parseObject(context, content ,null);
						break;
						
					case '[':
						parseArray(context, content, null);
						break;
					default:
						if(Character.isDigit(ch)){
							parseNumber(context, content, null, buffer);
						}else{
							throw new ParseException("Invalid object notation, not a valid JSON value",-1);
						}
				}
				
				if((ch  = (char)context.getCharacter()) == ','){
					ch = (char) readChar(context, content);
					skipSpaces(context, content);
					if(context.getCharacter() == ']')
						throw new ParseException("Invalid object notation, expected content after ','",-1);
				}
				ch = (char) context.getCharacter();
			}
		}
		
		if(context.getCharacter() != ']'){
			throw new ParseException("Invalid object notation, JSON content must end with ']'",-1);
		}
		
		readChar(context, content);
		skipSpaces(context, content);		
		onStructureEnd(context, objectName, StructureTypeEnum.ARRAY_TYPE);
	}

	/**
 *<p>Tries to parse the next boolean literal (true or false) in content.</p>
	 * @param context content parsing context.
	 * @param content A reader object that encodes a JSON object.
	 * @param name Element name, it is null if nested within an array.
	 * @param buffer Auxiliary buffer for internal processing.
	 * @throws IOException .-
	 * @throws ParseException .-
	 */
	private void parseBoolean(ParserContext context, Reader content, String name, StringBuilder buffer) throws IOException, ParseException {
		char ch = (char) context.getCharacter();
		String string;
		boolean value=false;
		
		buffer.delete(0, buffer.length());
		while(!context.isEof() &&  Character.isLetter(ch) ){
			buffer.append(ch);
			ch = (char) readChar(context, content);
		}
		
		string = buffer.toString();
		value = Boolean.parseBoolean(string);		
		onBoolean(context, name, value);		
		skipSpaces(context, content);	
	}

	/**
 *<p>Tries to parse the <code>null</code> literal content.</p>
	 * @param context content parsing context.
	 * @param content A reader object that encodes a JSON object.
	 * @param name Element name, it is null if nested within an array.
	 * @param buffer Auxiliary buffer for internal processing.
	 * @throws IOException .-
	 * @throws ParseException .-
	 */
	private void parseNull(ParserContext context, Reader content, String name, StringBuilder buffer) throws IOException, ParseException {
		char ch = (char) context.getCharacter();
		String string;
		
		buffer.delete(0, buffer.length());
		while(!context.isEof() &&  Character.isLetter(ch) ){
			buffer.append(ch);
			ch = (char) readChar(context, content);
		}
		
		string = buffer.toString();
		if(string.compareTo("null") == 0)
			this.onNull(context, name);
		else
			throw new ParseException("Invalid object notation, '+"+string+"' is not null literal",-1);
		skipSpaces(context, content);
	}

	/**
 *<p>Tries to parse the number literal content.</p>
	 * @param context content parsing context.
	 * @param content A reader object that encodes a JSON object.
	 * @param name Element name, it is null if nested within an array.
	 * @param buffer Auxiliary buffer for internal processing.
	 * @throws IOException .-
	 * @throws ParseException .-
	 */
	private void parseNumber(ParserContext context, Reader content, String name, StringBuilder buffer) throws IOException, ParseException {
		char ch = (char) context.getCharacter();
		String string;
		Number number;
		
		buffer.delete(0, buffer.length());
		while(!context.isEof() && (Character.toUpperCase(ch)=='E' || ch == '-' || ch == '+' || ch == '.' || Character.isDigit(ch)) ){
			buffer.append(ch);
			ch = (char) readChar(context, content);
		}
		
		string = buffer.toString();
		number = toNumber(string);
		if(number == null)
			throw new ParseException("Invalid object notation, '+"+string+"' is not a number",-1);
		onNumber(context, name, number);		
		skipSpaces(context, content);		
	}

	/**
 *<p>Tries to parse the an UNICODE character sequence (\\uHHHH), where H stands for a hexadecimal digit.</p>
	 * @param context content parsing context.
	 * @param content A reader object that encodes a JSON object.
	 * @param buffer Auxiliary buffer
	 * @throws IOException .-
	 * @throws ParseException .-
	 */
	private void parseUnicodeChar(ParserContext context, Reader content, StringBuilder buffer) throws IOException, ParseException {
		char ch = (char) readChar(context, content);
		int l = buffer.length(),i;
		final int MAX_UNICODE_CHARS = 4;
		final String EXCEPTION_MESSAGE="Invalid object notation, unicode literal expected";
		
		for(i = 0; i < MAX_UNICODE_CHARS && !context.isEof(); i++){
			buffer.append(ch);
			ch = (char) readChar(context, content);
		}
		
		if(i == MAX_UNICODE_CHARS){
			String substr = buffer.substring(l, buffer.length());
			ch = (char) Integer.parseInt(substr, 16);
			buffer.setCharAt(l, ch);
			buffer.delete(l+1, buffer.length());			
		}else
			throw new ParseException(EXCEPTION_MESSAGE,-1);		
	}
	
	private void parseCDATA(ParserContext context, Reader content, String name, StringBuilder buffer) throws IOException, ParseException {
		char ch = (char) readChar(context, content);		
		
		if( ch == '['){
			String string;
			buffer.delete(0, buffer.length());
			ch = (char) readChar(context, content);			
			
			main:while(!(context.isEof() || ch == ']')){
				if(ch == '\\'){				
					ch = (char) readChar(context, content);
					if(ch == ']')
						buffer.append(']');
					else{
						buffer.append('\\');
						continue main;
					}
				}else{
					buffer.append(ch);				
				}
				ch = (char) readChar(context, content);
			}
			
			if(context.isEof())
				throw new ParseException("Invalid object notation, unterminated data string",-1);	
			
			string = buffer.toString();
			this.onString(context, name, string);
			readChar(context, content);
			skipSpaces(context, content);	
		}else
			throw new ParseException("Invalid object notation, not a extended JSON data delimiter",-1);
	}
	
	/**
 *<p>Tries to parse a string.</p>
	 * @param context content parsing context.
	 * @param content A reader object that encodes a JSON object.
	 * @param name Element name, it is null if nested within an array.
	 * @param buffer
	 * @throws IOException
	 * @throws ParseException
	 */
	private void parseString(ParserContext context, Reader content, String name, StringBuilder buffer) throws IOException, ParseException {
		char ch = (char) readChar(context, content);		
		buffer.delete(0, buffer.length());
		String string;
		Date date;
		
		main:while(!(context.isEof() || ch == '"')){
			if(ch == '\\'){				
				ch = (char) readChar(context, content);
				switch(ch){
					case '\\':
						buffer.append(ch);
						break;
					case '/':
						buffer.append(ch);
						break;
					case 'b':
						buffer.append('\b');
						break;
					case 'f':
						buffer.append('\f'); 
						break;
					case 'n':
						buffer.append('\n');
						break;
					case 'r':
						buffer.append('\r');
						break;
					case 't':
						buffer.append('\t');												
						break;
					case '"':
						buffer.append('"');
						break;
					case 'u':
						parseUnicodeChar(context, content, buffer);
						ch = (char) context.getCharacter();
						continue main;
				}
			}else{
				buffer.append(ch);				
			}
			ch = (char) readChar(context, content);
		}
		
		if(context.isEof())
			throw new ParseException("Invalid object notation, unterminated character string",-1);	
		
		string = buffer.toString();
		date = toDate(string);
		if(date == null)
			this.onString(context, name, string);
		else
			this.onDate(context, name, date);
		
		readChar(context, content);
		skipSpaces(context, content);
	}

	/**
 *<p>Parses a JSON String.</p>
	 * @param content parsing context.
	 * @param content A character string that encodes a JSON object.
	 * @return The seame parsing context received as parameter.
	 * @throws ParseException  .-
	 * @throws IOException .-
	 */
	public ParserContext parse(ParserContext context, String content) throws IOException, ParseException{
		StringReader reader = new StringReader(content);
		return parse(context, reader);
	}
	
	/**
 *<p>Converts a string to a <code>java.lang.Number</code> instance.</p>
 *<p>
	 * The concrete  <code>java.lang.Number</code> subclass is selected according to following criteria:
	 * <li>If <code>string</code> matches "<code>^-?\d+\.\d+(e|E)(\+|-)\\d+$</code>" (scientific notation), then <code>java.lang.Double</code> is selected.</li>
	 * <li>If <code>string</code> matches "<code>^-?\\d+\.\d+$</code>" (fixed decimal), then <code>java.math.BigDecimal</code> is selected.</li>
	 * <li>If <code>string</code> matches "<code>-?\d+</code>" (integer), then <code>java.lang.Long</code> is selected.</li>
	 * </p>
	 * @param string Non null character string.
	 * @return An instance of <code>java.lang.Number</code> if <code>strings</code> is a valid number or <code>null</code> otherwise.
	 */
	public static Number toNumber(String string){
		Number number = null;
		if(INTEGER_PATTERN.matcher(string).matches())
			number = new Long(string);
		else if (FIXED_DECIMAL_PATTERN.matcher(string).matches())
			number = new BigDecimal(string);
		else if(SCIENTIFIC_NOTATION_PATTERN.matcher(string).matches())
			number = new Double(string);
		return number;
	}

	/**
 *<p>Converts a string to a <code>java.util.Date</code> instance.</p>
	 * @param string A non null string that encodes a date.
	 * @return A <code>java.util.Date</code> instance if the string represents a date or null otherwise.
	 * @throws ParseException .-
	 */
	public static Date toDate(String string) throws ParseException{
		Date date = null;
		string = string == null ? EMPTY_STRING : string.trim();
		
		if(SHORT_DATE_PATTERN.matcher(string).matches()){
			date = SHORT_DATE_FORMATTER.parse(string);
		}else if(LONG_DATE_PATTERN_AM_PM.matcher(string).matches()){
			date = LONG_DATE_PARSER_AM_PM.parse(string);
		}else if(LONG_DATE_PATTERN_24H.matcher(string).matches()){
			date = LONG_DATE_PARSER_24H.parse(string);
		}
		
		return date;
	}	
}
