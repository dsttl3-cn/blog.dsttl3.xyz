import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratePosts {

    public static void main(String[] args) {
        try {
            // 读取Markdown目录下的所有Markdown文件
            Path postsDir = Paths.get(System.getProperty("user.dir"), "Markdown");
            List<Path> markdownFiles = new ArrayList<>();
            
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(postsDir, "*.md")) {
                for (Path entry : stream) {
                    markdownFiles.add(entry);
                }
            }
            
            // 存储文章信息的列表
            List<Post> posts = new ArrayList<>();
            
            // 处理每个Markdown文件
            for (Path file : markdownFiles) {
                String content = Files.readString(file);
                String fileName = file.getFileName().toString();
                String id = fileName.replace(".md", "");
                
                // 提取标题
                String title = extractTitle(content, id);
                
                // 提取摘要（第一个段落）
                String summary = extractSummary(content);
                
                // 获取文件创建时间
                String createdAt = getFileCreationTime(file);
                
                // 添加默认封面图片
                String cover = "https://images.pexels.com/photos/1181675/pexels-photo-1181675.jpeg?auto=compress&cs=tinysrgb&w=1200";
                
                // 创建文章对象
                Post post = new Post(id, title, summary, createdAt, Arrays.asList("默认标签"), cover);
                posts.add(post);
            }
            
            // 按创建时间倒序排序
            posts.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
            
            // 生成JSON
            generateJson(posts);
            
            System.out.println("生成了 " + posts.size() + " 篇文章的列表");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // 提取标题
    private static String extractTitle(String content, String defaultTitle) {
        Pattern pattern = Pattern.compile("^#\\s+(.+)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return defaultTitle;
    }
    
    // 提取摘要
    private static String extractSummary(String content) {
        Pattern pattern = Pattern.compile("^#\\s+.+\\n\\n(.+)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
    
    // 获取文件创建时间
    private static String getFileCreationTime(Path file) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
        Date creationTime = new Date(attrs.creationTime().toMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(creationTime);
    }
    
    // 生成JSON文件
    private static void generateJson(List<Post> posts) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode postsArray = mapper.createArrayNode();
        
        for (Post post : posts) {
            ObjectNode postNode = mapper.createObjectNode();
            postNode.put("id", post.getId());
            postNode.put("title", post.getTitle());
            postNode.put("summary", post.getSummary());
            postNode.put("createdAt", post.getCreatedAt());
            
            ArrayNode tagsArray = mapper.createArrayNode();
            for (String tag : post.getTags()) {
                tagsArray.add(tag);
            }
            postNode.set("tags", tagsArray);
            
            postNode.put("cover", post.getCover());
            postsArray.add(postNode);
        }
        
        Path outputPath = Paths.get(System.getProperty("user.dir"), "api", "posts.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputPath.toFile(), postsArray);
    }
    
    // 文章类
    static class Post {
        private String id;
        private String title;
        private String summary;
        private String createdAt;
        private List<String> tags;
        private String cover;
        
        public Post(String id, String title, String summary, String createdAt, List<String> tags, String cover) {
            this.id = id;
            this.title = title;
            this.summary = summary;
            this.createdAt = createdAt;
            this.tags = tags;
            this.cover = cover;
        }
        
        public String getId() {
            return id;
        }
        
        public String getTitle() {
            return title;
        }
        
        public String getSummary() {
            return summary;
        }
        
        public String getCreatedAt() {
            return createdAt;
        }
        
        public List<String> getTags() {
            return tags;
        }
        
        public String getCover() {
            return cover;
        }
    }
}
