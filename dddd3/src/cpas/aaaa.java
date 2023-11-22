package cpas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class aaaa {
  public static void main(String[] args) {
    try {
      // 파이썬 인터프리터 경로와 스크립트 파일 경로
      String pythonPath = "C:\\Python\\python.exe";
      String scriptPath = "C:\\Program Files\\ss\\20.py";
      
      // 프로세스 빌더를 사용하여 명령어와 인수 분리
      ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath);
      Process p = processBuilder.start();
      
      // 표준 출력 스트림
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        System.out.println(line);
      }
      
      // 표준 에러 스트림 (옵션)
      BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
      while ((line = err.readLine()) != null) {
        System.err.println(line);
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
