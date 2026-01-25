# 在博客详情页渲染 Markdown 和代码高亮

博客文章通常使用 Markdown 编写, 这样既方便书写, 又便于存储和版本管理。

## 使用 marked 解析 Markdown

在前端, 可以使用 `marked` 将 Markdown 字符串转换成 HTML:

``` js
import { marked } from "marked";

const html = marked.parse(markdownText);
```

## 使用 highlight.js 做代码高亮

`highlight.js` 可以为代码块自动匹配语言并添加颜色样式:

``` js
import hljs from "highlight.js";

marked.setOptions({
  highlight(code, lang) {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(code, { language: lang }).value;
    }
    return hljs.highlightAuto(code).value;
  },
  langPrefix: "hljs language-"
});
```

在样式层面, 本示例直接引入了内置主题:

``` js
import "highlight.js/styles/github-dark.css";
```

这样, 所有 Markdown 中的代码块都会带有主题色彩和背景。

