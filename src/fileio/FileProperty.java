package fileio;

public enum FileProperty {
  HEADER_SIZE(3),
  HEADER_LINE_NUM_INDEX(0),
  HEADER_BPM_INDEX(1),
  HEADER_LENGTH_INDEX(2);
  
  private int value;
  
  FileProperty(int value) {
    this.value = value;
  }
  
  public int getValue() {
    return this.value;
  }
}
