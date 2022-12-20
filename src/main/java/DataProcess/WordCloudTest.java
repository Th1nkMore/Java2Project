package DataProcess;

import DataLoader.repo;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundaryBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizer.core.WhiteSpaceWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Word cloud test.
 */
public class WordCloudTest {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    public static void main(String[] args) throws IOException, ParseException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        List<String> StopWordList = new ArrayList<>();
        try {
            File StopWord = new File("src/main/resources/Kumo/stopwords.txt");
            BufferedReader reader = new BufferedReader(new FileReader(StopWord));
            reader.lines().forEach(StopWordList::add);
        }catch (FileNotFoundException e){
            System.err.println("File not found!");
        }
        frequencyAnalyzer.setMinWordLength(2);
        frequencyAnalyzer.setStopWords(StopWordList);
        frequencyAnalyzer.setWordTokenizer(new WhiteSpaceWordTokenizer());
        frequencyAnalyzer.setWordFrequenciesToReturn(500);
        repo repo = new repo("spring-boot", "spring-projects");
        repo.get_issueInfo();
        List<String> origin = repo.issues.stream().map(DataLoader.repo.Issue::getDescription).filter(Objects::nonNull).map(e->e.replaceAll("[^a-zA-Z ]","")).collect(Collectors.toList());
//        origin.forEach(System.out::println);
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(origin);
        final Dimension dimension = new Dimension(2499, 2500);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);

        wordCloud.setPadding(3);
        wordCloud.setFontScalar(new SqrtFontScalar(50, 70));
//设置词云显示的三种颜色，越靠前设置表示词频越高的词语的颜色
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
        wordCloud.setBackgroundColor(new Color(255, 255, 255));
//        wordCloud.setBackground(new CircleBackground(200));
        wordCloud.setBackground(new PixelBoundaryBackground(Files.newInputStream(Paths.get("src/main/resources/Kumo/image/test2.png"))));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("5.png");
    }
}
