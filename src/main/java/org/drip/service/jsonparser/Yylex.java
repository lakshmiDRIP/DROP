
package org.drip.service.jsonparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>Yylex</i> is an Adaptation of the Yylex Class from the RFC4627 compliant JSON Simple
 * 	(https://code.google.com/p/json-simple/). It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Error - Unexpected Character</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/jsonparser/README.md">RFC4627 Compliant JSON Message Parser</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public class Yylex
{

	  /** This character denotes the end of file */
	  public static final int YYEOF = -1;

	  /** initial size of the lookahead buffer */
	  private static final int ZZ_BUFFERSIZE = 16384;

	  /** lexical states */
	  public static final int YYINITIAL = 0;

	  /**
	   * Lexical State - BEGIN
	   */

	  public static final int STRING_BEGIN = 2;

	  /**
	   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
	   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
	   *                  at the beginning of a line
	   * l is of the form l = 2*k, k a non negative integer
	   */
	  private static final int ZZ_LEXSTATE[] = { 
	     0,  0,  1, 1
	  };

	  /** 
	   * Translates characters to character classes
	   */
	  private static final String ZZ_CMAP_PACKED = 
	    "\11\0\1\7\1\7\2\0\1\7\22\0\1\7\1\0\1\11\10\0"+
	    "\1\6\1\31\1\2\1\4\1\12\12\3\1\32\6\0\4\1\1\5"+
	    "\1\1\24\0\1\27\1\10\1\30\3\0\1\22\1\13\2\1\1\21"+
	    "\1\14\5\0\1\23\1\0\1\15\3\0\1\16\1\24\1\17\1\20"+
	    "\5\0\1\25\1\0\1\26\uff82\0";

	  /** 
	   * Translates characters to character classes
	   */
	  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

	  /** 
	   * Translates DFA states to action switch labels.
	   */
	  private static final int [] ZZ_ACTION = zzUnpackAction();

	  private static final String ZZ_ACTION_PACKED_0 =
	    "\2\0\2\1\1\2\1\3\1\4\3\1\1\5\1\6"+
	    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\5\0"+
	    "\1\14\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
	    "\1\0\1\25\1\0\1\25\4\0\1\26\1\27\2\0"+
	    "\1\30";

	  private static int [] zzUnpackAction() {
	    int [] result = new int[45];
	    int offset = 0;
	    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
	    return result;
	  }

	  private static int zzUnpackAction(String packed, int offset, int [] result) {
	    int i = 0;       /* index in packed string  */
	    int j = offset;  /* index in unpacked array */
	    int l = packed.length();
	    while (i < l) {
	      int count = packed.charAt(i++);
	      int value = packed.charAt(i++);
	      do result[j++] = value; while (--count > 0);
	    }
	    return j;
	  }


	  /** 
	   * Translates a state to a row index in the transition table
	   */
	  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

	  private static final String ZZ_ROWMAP_PACKED_0 =
	    "\0\0\0\33\0\66\0\121\0\154\0\207\0\66\0\242"+
	    "\0\275\0\330\0\66\0\66\0\66\0\66\0\66\0\66"+
	    "\0\363\0\u010e\0\66\0\u0129\0\u0144\0\u015f\0\u017a\0\u0195"+
	    "\0\66\0\66\0\66\0\66\0\66\0\66\0\66\0\66"+
	    "\0\u01b0\0\u01cb\0\u01e6\0\u01e6\0\u0201\0\u021c\0\u0237\0\u0252"+
	    "\0\66\0\66\0\u026d\0\u0288\0\66";

	  private static int [] zzUnpackRowMap() {
	    int [] result = new int[45];
	    int offset = 0;
	    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
	    return result;
	  }

	  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
	    int i = 0;  /* index in packed string  */
	    int j = offset;  /* index in unpacked array */
	    int l = packed.length();
	    while (i < l) {
	      int high = packed.charAt(i++) << 16;
	      result[j++] = high | packed.charAt(i++);
	    }
	    return j;
	  }

	  /** 
	   * The transition table of the DFA
	   */
	  private static final int ZZ_TRANS [] = {
	    2, 2, 3, 4, 2, 2, 2, 5, 2, 6, 
	    2, 2, 7, 8, 2, 9, 2, 2, 2, 2, 
	    2, 10, 11, 12, 13, 14, 15, 16, 16, 16, 
	    16, 16, 16, 16, 16, 17, 18, 16, 16, 16, 
	    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 
	    16, 16, 16, 16, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, 4, 19, 20, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, 5, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    21, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, 22, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    23, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, 16, 16, 16, 16, 16, 16, 16, 
	    16, -1, -1, 16, 16, 16, 16, 16, 16, 16, 
	    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 
	    -1, -1, -1, -1, -1, -1, -1, -1, 24, 25, 
	    26, 27, 28, 29, 30, 31, 32, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    33, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, 34, 35, -1, -1, 
	    34, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    36, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, 37, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, 39, -1, 39, -1, 39, -1, -1, 
	    -1, -1, -1, 39, 39, -1, -1, -1, -1, 39, 
	    39, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, 33, -1, 20, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, 
	    -1, -1, -1, -1, -1, 42, -1, 42, -1, 42, 
	    -1, -1, -1, -1, -1, 42, 42, -1, -1, -1, 
	    -1, 42, 42, -1, -1, -1, -1, -1, -1, -1, 
	    -1, -1, 43, -1, 43, -1, 43, -1, -1, -1, 
	    -1, -1, 43, 43, -1, -1, -1, -1, 43, 43, 
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, 
	    -1, 44, -1, 44, -1, -1, -1, -1, -1, 44, 
	    44, -1, -1, -1, -1, 44, 44, -1, -1, -1, 
	    -1, -1, -1, -1, -1, 
	  };

	  /* error codes */
	  private static final int ZZ_UNKNOWN_ERROR = 0;
	  private static final int ZZ_NO_MATCH = 1;
	  private static final int ZZ_PUSHBACK_2BIG = 2;

	  /* error messages for the codes above */
	  private static final String ZZ_ERROR_MSG[] = {
	    "Unkown internal scanner error",
	    "Error: could not match input",
	    "Error: pushback value was too large"
	  };

	  /**
	   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
	   */
	  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

	  private static final String ZZ_ATTRIBUTE_PACKED_0 =
	    "\2\0\1\11\3\1\1\11\3\1\6\11\2\1\1\11"+
	    "\5\0\10\11\1\0\1\1\1\0\1\1\4\0\2\11"+
	    "\2\0\1\11";

	  private static int [] zzUnpackAttribute() {
	    int [] result = new int[45];
	    int offset = 0;
	    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
	    return result;
	  }

	  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
	    int i = 0;       /* index in packed string  */
	    int j = offset;  /* index in unpacked array */
	    int l = packed.length();
	    while (i < l) {
	      int count = packed.charAt(i++);
	      int value = packed.charAt(i++);
	      do result[j++] = value; while (--count > 0);
	    }
	    return j;
	  }

	  /** the input device */
	  private Reader zzReader;

	  /** the current state of the DFA */
	  private int zzState;

	  /** the current lexical state */
	  private int zzLexicalState = YYINITIAL;

	  /** this buffer contains the current text to be matched and is
	      the source of the yytext() string */
	  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

	  /** the textposition at the last accepting state */
	  private int zzMarkedPos;

	  /** the current text position in the buffer */
	  private int zzCurrentPos;

	  /** startRead marks the beginning of the yytext() string in the buffer */
	  private int zzStartRead;

	  /** endRead marks the last character in the buffer, that has been read
	      from input */
	  private int zzEndRead;

	  /** number of newlines encountered up to the start of the matched text */
	  int yyline;

	  /** the number of characters up to the start of the matched text */
	  private int yychar;

	  /**
	   * the number of characters from the last newline up to the start of the 
	   * matched text
	   */
	  int yycolumn;

	  /** 
	   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
	   */
	  boolean zzAtBOL = true;

	  /** zzAtEOF == true <=> the scanner is at the EOF */
	  private boolean zzAtEOF;

	  /* user code: */
	private StringBuffer sb=new StringBuffer();

	int getPosition(){
	        return yychar;
	}



	  /**
	   * Creates a new scanner
	   * There is also a InputStream version of this constructor.
	   *
	   * @param   in  the Reader to read input from.
	   */
	  public Yylex(Reader in) {
	    this.zzReader = in;
	  }

	  /**
	   * Creates a new scanner.
	   * There is also Reader version of this constructor.
	   *
	   * @param   in  the Inputstream to read input from.
	   */
	  Yylex(InputStream in) {
	    this(new InputStreamReader(in));
	  }

	  /** 
	   * Unpacks the compressed character translation table.
	   *
	   * @param packed   the packed character translation table
	   * @return         the unpacked character translation table
	   */
	  private static char [] zzUnpackCMap(String packed) {
	    char [] map = new char[0x10000];
	    int i = 0;  /* index in packed string  */
	    int j = 0;  /* index in unpacked array */
	    while (i < 90) {
	      int  count = packed.charAt(i++);
	      char value = packed.charAt(i++);
	      do map[j++] = value; while (--count > 0);
	    }
	    return map;
	  }


	  /**
	   * Refills the input buffer.
	   *
	   * @return      <code>false</code>, iff there was new input.
	   * 
	   * @exception   IOException  if any I/O-Error occurs
	   */
	  private boolean zzRefill() throws IOException {

	    /* first: make room (if you can) */
	    if (zzStartRead > 0) {
	      System.arraycopy(zzBuffer, zzStartRead,
	                       zzBuffer, 0,
	                       zzEndRead-zzStartRead);

	      /* translate stored positions */
	      zzEndRead-= zzStartRead;
	      zzCurrentPos-= zzStartRead;
	      zzMarkedPos-= zzStartRead;
	      zzStartRead = 0;
	    }

	    /* is the buffer big enough? */
	    if (zzCurrentPos >= zzBuffer.length) {
	      /* if not: blow it up */
	      char newBuffer[] = new char[zzCurrentPos*2];
	      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
	      zzBuffer = newBuffer;
	    }

	    /* finally: fill the buffer with new input */
	    int numRead = zzReader.read(zzBuffer, zzEndRead,
	                                            zzBuffer.length-zzEndRead);

	    if (numRead > 0) {
	      zzEndRead+= numRead;
	      return false;
	    }
	    // unlikely but not impossible: read 0 characters, but not at end of stream    
	    if (numRead == 0) {
	      int c = zzReader.read();
	      if (c == -1) {
	        return true;
	      } else {
	        zzBuffer[zzEndRead++] = (char) c;
	        return false;
	      }     
	    }

	        // numRead < 0
	    return true;
	  }

	    
	  /**
	   * Closes the input stream.
	   * 
	   * @throws IOException Thrown if the Inputs are Invalid
	   */
	  public final void yyclose() throws IOException {
	    zzAtEOF = true;            /* indicate end of file */
	    zzEndRead = zzStartRead;  /* invalidate buffer    */

	    if (zzReader != null)
	      zzReader.close();
	  }


	  /**
	   * Resets the scanner to read from a new input stream.
	   * Does not close the old reader.
	   *
	   * All internal variables are reset, the old input stream 
	   * <b>cannot</b> be reused (internal buffer is discarded and lost).
	   *
	   * @param reader   the new input stream 
	   */
	  public final void yyreset(Reader reader) {
	    zzReader = reader;
	    zzAtBOL  = true;
	    zzAtEOF  = false;
	    zzEndRead = zzStartRead = 0;
	    zzCurrentPos = zzMarkedPos = 0;
	    yyline = yychar = yycolumn = 0;
	    zzLexicalState = YYINITIAL;
	  }


	  /**
	   * Returns the current lexical state.
	   * 
	   * @return The Current Lexical State.
	   */

	  public final int yystate() {
	    return zzLexicalState;
	  }


	  /**
	   * Enters a new lexical state
	   *
	   * @param newState the new lexical state
	   */
	  public final void yybegin(int newState) {
	    zzLexicalState = newState;
	  }


	  /**
	   * Returns the text matched by the current regular expression.
	   * 
	   * @return The Text.
	   */

	  public final String yytext() {
	    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
	  }


	  /**
	   * Returns the character at position <b>pos</b> from the 
	   * matched text. 
	   * 
	   * It is equivalent to yytext().charAt(pos), but faster
	   *
	   * @param pos the position of the character to fetch. 
	   *            A value from 0 to yylength()-1.
	   *
	   * @return the character at position pos
	   */
	  public final char yycharat(int pos) {
	    return zzBuffer[zzStartRead+pos];
	  }


	  /**
	   * Returns the length of the matched text region.
	   * 
	   * @return The Length.
	   */
	  public final int yylength() {
	    return zzMarkedPos-zzStartRead;
	  }


	  /**
	   * Reports an error that occured while scanning.
	   *
	   * In a wellformed scanner (no or only correct usage of 
	   * yypushback(int) and a match-all fallback rule) this method 
	   * will only be called with things that "Can't Possibly Happen".
	   * If this method is called, something is seriously wrong
	   * (e.g. a JFlex bug producing a faulty scanner etc.).
	   *
	   * Usual syntax/scanner level error handling should be done
	   * in error fallback rules.
	   *
	   * @param   errorCode  the code of the errormessage to display
	   */
	  private void zzScanError(int errorCode) {
	    String message;
	    try {
	      message = ZZ_ERROR_MSG[errorCode];
	    }
	    catch (ArrayIndexOutOfBoundsException e) {
	      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
	    }

	    throw new Error(message);
	  } 


	  /**
	   * Pushes the specified amount of characters back into the input stream.
	   *
	   * They will be read again by then next call of the scanning method
	   *
	   * @param number  the number of characters to be read again.
	   *                This number must not be greater than yylength()!
	   */
	  public void yypushback(int number)  {
	    if ( number > yylength() )
	      zzScanError(ZZ_PUSHBACK_2BIG);

	    zzMarkedPos -= number;
	  }


	  /**
	   * Resumes scanning until the next regular expression is matched,
	   * the end of input is encountered or an I/O-Error occurs.
	   *
	   * @return      the next token
	   * 
	   * @throws   IOException  if any I/O-Error occurs
	   * 
	   * @throws ParseException Thrown if Inputs are Invalid
	   */

	  public Yytoken yylex() throws IOException, ParseException {
	    int zzInput;
	    int zzAction;

	    // cached fields:
	    int zzCurrentPosL;
	    int zzMarkedPosL;
	    int zzEndReadL = zzEndRead;
	    char [] zzBufferL = zzBuffer;
	    char [] zzCMapL = ZZ_CMAP;

	    int [] zzTransL = ZZ_TRANS;
	    int [] zzRowMapL = ZZ_ROWMAP;
	    int [] zzAttrL = ZZ_ATTRIBUTE;

	    while (true) {
	      zzMarkedPosL = zzMarkedPos;

	      yychar+= zzMarkedPosL-zzStartRead;

	      zzAction = -1;

	      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
	  
	      zzState = ZZ_LEXSTATE[zzLexicalState];


	      zzForAction: {
	        while (true) {
	    
	          if (zzCurrentPosL < zzEndReadL)
	            zzInput = zzBufferL[zzCurrentPosL++];
	          else if (zzAtEOF) {
	            zzInput = YYEOF;
	            break zzForAction;
	          }
	          else {
	            // store back cached positions
	            zzCurrentPos  = zzCurrentPosL;
	            zzMarkedPos   = zzMarkedPosL;
	            boolean eof = zzRefill();
	            // get translated positions and possibly new buffer
	            zzCurrentPosL  = zzCurrentPos;
	            zzMarkedPosL   = zzMarkedPos;
	            zzBufferL      = zzBuffer;
	            zzEndReadL     = zzEndRead;
	            if (eof) {
	              zzInput = YYEOF;
	              break zzForAction;
	            }
	            else {
	              zzInput = zzBufferL[zzCurrentPosL++];
	            }
	          }
	          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
	          if (zzNext == -1) break zzForAction;
	          zzState = zzNext;

	          int zzAttributes = zzAttrL[zzState];
	          if ( (zzAttributes & 1) == 1 ) {
	            zzAction = zzState;
	            zzMarkedPosL = zzCurrentPosL;
	            if ( (zzAttributes & 8) == 8 ) break zzForAction;
	          }

	        }
	      }

	      // store back cached position
	      zzMarkedPos = zzMarkedPosL;

	      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
	        case 11: 
	          { sb.append(yytext());
	          }
	        case 25: break;
	        case 4: 
	          { sb.delete(0, sb.length());yybegin(STRING_BEGIN);
	          }
	        case 26: break;
	        case 16: 
	          { sb.append('\b');
	          }
	        case 27: break;
	        case 6: 
	          { return new Yytoken(Yytoken.TYPE_RIGHT_BRACE,null);
	          }
	        case 28: break;
	        case 23: 
	          { Boolean val=Boolean.valueOf(yytext()); return new Yytoken(Yytoken.TYPE_VALUE, val);
	          }
	        case 29: break;
	        case 22: 
	          { return new Yytoken(Yytoken.TYPE_VALUE, null);
	          }
	        case 30: break;
	        case 13: 
	          { yybegin(YYINITIAL);return new Yytoken(Yytoken.TYPE_VALUE, sb.toString());
	          }
	        case 31: break;
	        case 12: 
	          { sb.append('\\');
	          }
	        case 32: break;
	        case 21: 
	          { Double val=Double.valueOf(yytext()); return new Yytoken(Yytoken.TYPE_VALUE, val);
	          }
	        case 33: break;
	        case 1: 
	          { throw new ParseException(yychar, ParseException.ERROR_UNEXPECTED_CHAR, yycharat (0));
	          }
	        case 34: break;
	        case 8: 
	          { return new Yytoken(Yytoken.TYPE_RIGHT_SQUARE,null);
	          }
	        case 35: break;
	        case 19: 
	          { sb.append('\r');
	          }
	        case 36: break;
	        case 15: 
	          { sb.append('/');
	          }
	        case 37: break;
	        case 10: 
	          { return new Yytoken(Yytoken.TYPE_COLON,null);
	          }
	        case 38: break;
	        case 14: 
	          { sb.append('"');
	          }
	        case 39: break;
	        case 5: 
	          { return new Yytoken(Yytoken.TYPE_LEFT_BRACE,null);
	          }
	        case 40: break;
	        case 17: 
	          { sb.append('\f');
	          }
	        case 41: break;
	        case 24: 
	          { try{
	                                                                                                                int ch=Integer.parseInt(yytext().substring(2),16);
	                                                                                                                sb.append((char)ch);
	                                                                                                        }
	                                                                                                        catch(Exception e){
	                                                                                                                throw new ParseException(yychar, ParseException.ERROR_UNEXPECTED_EXCEPTION, e);
	                                                                                                        }
	          }
	        case 42: break;
	        case 20: 
	          { sb.append('\t');
	          }
	        case 43: break;
	        case 7: 
	          { return new Yytoken(Yytoken.TYPE_LEFT_SQUARE,null);
	          }
	        case 44: break;
	        case 2: 
	          { Long val=Long.valueOf(yytext()); return new Yytoken(Yytoken.TYPE_VALUE, val);
	          }
	        case 45: break;
	        case 18: 
	          { sb.append('\n');
	          }
	        case 46: break;
	        case 9: 
	          { return new Yytoken(Yytoken.TYPE_COMMA,null);
	          }
	        case 47: break;
	        case 3: 
	          { 
	          }
	        case 48: break;
	        default: 
	          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
	            zzAtEOF = true;
	            return null;
	          } 
	          else {
	            zzScanError(ZZ_NO_MATCH);
	          }
	      }
	    }
	  }
	}
