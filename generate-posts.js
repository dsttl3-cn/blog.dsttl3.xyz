const fs = require('fs');
const path = require('path');

// 读取Markdown目录下的所有Markdown文件
const postsDir = path.join(__dirname, 'Markdown');
const files = fs.readdirSync(postsDir).filter(file => file.endsWith('.md'));

const posts = [];

files.forEach(file => {
  const filePath = path.join(postsDir, file);
  const content = fs.readFileSync(filePath, 'utf-8');
  const id = file.replace('.md', '');
  
  // 提取标题
  const titleMatch = content.match(/^#\s+(.+)$/m);
  const title = titleMatch ? titleMatch[1] : id;
  
  // 提取摘要（第一个段落）
  const summaryMatch = content.match(/^#\s+.+\n\n(.+)$/m);
  const summary = summaryMatch ? summaryMatch[1].trim() : '';
  
  // 获取文件创建时间
  const stats = fs.statSync(filePath);
  const createdAt = stats.birthtime.toISOString();
  
  // 添加默认封面图片
  const cover = `https://images.pexels.com/photos/1181675/pexels-photo-1181675.jpeg?auto=compress&cs=tinysrgb&w=1200`;
  
  posts.push({
    id,
    title,
    summary,
    createdAt,
    tags: ['默认标签'], // 默认标签，可根据需要修改
    cover
  });
});

// 按创建时间倒序排序
posts.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

// 写入posts.json
const outputPath = path.join(__dirname, 'api', 'posts.json');
fs.writeFileSync(outputPath, JSON.stringify(posts, null, 2), 'utf-8');

console.log(`生成了 ${posts.length} 篇文章的列表`);
