# blog.dsttl3.xyz

一个基于静态文件的博客系统，托管在GitHub上，支持自动生成博客文章列表。

## 项目介绍

这是一个简单的博客系统，使用Markdown编写博客文章，通过Node.js脚本自动生成博客文章列表JSON文件，支持GitHub Actions自动部署和更新。

## 项目结构

```
blog.dsttl3.xyz/
├── api/
│   └── posts.json          # 自动生成的博客文章列表JSON
├── posts/                  # Markdown博客文章目录
│   ├── hello-vue3.md
│   └── markdown-and-code.md
├── assets/                 # 静态资源文件
├── .github/
│   └── workflows/
│       └── generate-posts.yml  # GitHub Actions工作流
├── generate-posts.js       # 生成博客文章列表的脚本
├── index.html              # 博客主页
└── README.md               # 项目说明文档
```

## 功能特性

- 使用Markdown编写博客文章
- 自动生成博客文章列表JSON
- 支持GitHub Actions自动更新
- 静态文件部署，无需服务器
- 支持自定义域名

## 技术栈

- HTML/CSS/JavaScript
- Node.js
- GitHub Actions
- Markdown

## 安装与使用

### 本地开发

1. 克隆项目到本地

```bash
git clone https://github.com/yourusername/blog.dsttl3.xyz.git
cd blog.dsttl3.xyz
```

2. 运行生成脚本

```bash
node generate-posts.js
```

3. 在浏览器中打开 `index.html` 查看博客

### 添加新文章

1. 在 `posts/` 目录下创建新的Markdown文件，文件名即为文章ID
2. 文章格式：
   ```markdown
   # 文章标题
   
   文章摘要内容，这将作为文章列表中的摘要显示。
   
   ## 章节标题
   
   文章内容...
   ```
3. 提交到GitHub，GitHub Actions会自动生成并更新 `api/posts.json`

## GitHub Actions自动更新机制

项目配置了GitHub Actions工作流，当有新的提交推送到 `main` 分支或创建PR时，会自动执行以下操作：

1. 检查代码
2. 设置Node.js环境
3. 运行 `generate-posts.js` 脚本生成最新的 `posts.json`
4. 自动提交并推送更新后的 `posts.json`

## 自定义配置

### 修改生成脚本

可以修改 `generate-posts.js` 来自定义：

- 默认标签：修改 `tags` 数组
- 封面图片：修改 `cover` 变量
- JSON输出路径：修改 `outputPath` 变量

### 自定义域名

1. 在项目根目录创建 `CNAME` 文件
2. 在文件中写入你的自定义域名，如：
   ```
   blog.dsttl3.xyz
   ```
3. 在DNS提供商处添加CNAME记录，指向 `yourusername.github.io`

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request！

## 联系方式

如果有任何问题或建议，欢迎通过GitHub Issues联系我。
