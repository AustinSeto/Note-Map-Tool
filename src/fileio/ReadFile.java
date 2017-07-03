/*
 * MIT License
 * 
 * Copyright (c) 2017 Austin Seto
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package fileio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class containing methods for reading files.
 *
 * @author Austin Seto
 */
public class ReadFile {
  
  // Constants
  private static final int HEADER_SIZE = 3;
  private static final int HEADER_LINE_NUM_INDEX = 0;
  private static final int HEADER_BPM_INDEX = 1;
  private static final int HEADER_LENGTH_INDEX = 2;
    
  /**
   * Gets the relevant contents of a file in the proper file format for the Note Map Creator.
   * 
   * Each stream of notes will be given one string in the returned array.
   * @param fileName name of the file to open
   * @return an array of the stream of notes in the file
   * @throws FileNotFoundException if input file cannot be found
   */
  public static String[] getFileContents(String fileName) throws FileNotFoundException {
    FileReader fr = new FileReader(fileName);
    BufferedReader br = new BufferedReader(fr);
    int header[] = getHeader(br);
    String contents[] = new String[header[HEADER_LINE_NUM_INDEX]];
    for (int i = 0; i < header[HEADER_LINE_NUM_INDEX]; i++) {
      // Get every line
      contents[i] = processLine(header[HEADER_LENGTH_INDEX], br);
    }
    return contents;
  }
  
  /**
   * Gets the BPM described in the file with fileName, assuming it exists and is in the proper
   * file format for the Note Map Creator.
   * 
   * @param fileName name of the file
   * @return the BPM of the song described in the file
   * @throws FileNotFoundException if the file cannot be found
   */
  public static int getBpm(String fileName) throws FileNotFoundException {
    FileReader fr = new FileReader(fileName);
    BufferedReader br = new BufferedReader(fr);
    int header[] = getHeader(br);
    return header[HEADER_BPM_INDEX];
  }
  
  private static int[] getHeader(BufferedReader br) {
    int header[] = new int[HEADER_SIZE];
    try {
      String contents = br.readLine();
      String nums[] = contents.split(" ");
      for (int i = 0; i < HEADER_SIZE; i++) {
        header[i] = Integer.getInteger(nums[i], 0);
      }
    } catch (IOException e) {
      // If header cannot be read for some reason, return a zero header
      for (int i = 0; i < HEADER_SIZE; i++) {
        header[i] = 0;
      }
    }
    return header;
  }
  
  /**
   * Processes a single line from the file, and gets a string of number to represent it.
   * 
   * One line is one stream of notes. 
   * @param fileName name of the file
   * @param length length of the line
   * @return a processed line from the file
   */
  private static String processLine(int length, BufferedReader br) {
    String raw = readRawLine(br);
    String processed = "";
    // Loop through every character in the raw string
    int numChars = processed.length();
    int curr_char;
    for (int i = 0; i < numChars; i++) {
      // If character is a space, ignore
      if (raw.charAt(i) != ' ') {
        // Otherwise process it as a number
        curr_char = Character.getNumericValue(raw.charAt(i));
        if (curr_char == 0) {
          processed += "0";
        } else {
          processed += "1";
        }
      }
    }
    // Fix string to have proper number of characters
    if (processed.length() > length) {
      // Cut string down to proper length
      processed = processed.substring(0, length);
    } else {
      while (processed.length() < length) {
        // Extend string to proper length
        processed += "0";
      }
    }
    return processed;
  }
  
  private static String readRawLine(BufferedReader br) {
    String output;
    try {
      output = br.readLine();
    } catch (IOException e) {
      // If some kind of problem getting line, treat it as a blank one
      output = "";
    }
    return output;
  }
}
