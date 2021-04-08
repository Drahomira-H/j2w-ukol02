package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class QuotePictureController {

    private final Random random;

    public QuotePictureController() {
        random = new Random();
    }

    private static List<String> readAllLines(String resource) throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }
    // příklad volání: readAllLines("citaty.txt")

    public String getLine(List<String> lines, int index) {
        return lines.get(index);
    }

    @GetMapping("/")
    public ModelAndView quotePicture() throws IOException {
        int randomNum = random.nextInt(8);
        int randomNum2 = random.nextInt(8);  // jinak jeden citát na stále stejném obrázku

        ModelAndView result = new ModelAndView("quote_picture");
//        System.out.println(readAllLines("citaty.txt"));  //[Debugging /de·bugh·ing/ (verb): The Classic Mystery Game where you are the detective, the victim, and the murderer., A user interface is like a joke.
//        System.out.println(getLine(readAllLines("citaty.txt"), 1)); //A user interface is like a joke. If you have to explain it, it's not that good.
        result.addObject("num", randomNum);
        result.addObject("pic", String.format("/images/pic%d.jpg", randomNum));
        result.addObject("quote", getLine(readAllLines("citaty.txt"), randomNum2));
        return result;
    }
}
