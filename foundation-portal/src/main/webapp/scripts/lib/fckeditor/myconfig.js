
// 1. 自定义 ToolbarSet

FCKConfig.ToolbarSets["simple"] = [['Bold', 'Italic', 'Underline'],
		['Link', 'Unlink'], ['Image', 'SpecialChar'], ['FontName'],
		['FontSize'], ['TextColor', 'BGColor'],];

FCKConfig.ToolbarSets["shopBar"] = [['NewPage', 'RemoveFormat'],
		['Bold', 'Italic', 'Underline'], ['Subscript', 'Superscript'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight'], ['Link', 'Unlink'],
		['Image', 'SpecialChar'], ['Table'],
		['OrderedList', 'UnorderedList', '-', 'Outdent', 'Indent'],
		['FontName'], ['FontSize'], ['TextColor', 'BGColor'], ['FitWindow']];

FCKConfig.ToolbarSets["admin"] = [
		['Source', 'DocProps', '-', 'Save', 'NewPage', 'Preview', '-',
				'Templates'],
		['Cut', 'Copy', 'Paste', 'PasteText', 'PasteWord', '-', 'Print',
				'SpellCheck'],
		['Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll',
				'RemoveFormat'],
		['Bold', 'Italic', 'Underline', 'StrikeThrough', '-', 'Subscript',
				'Superscript'],
		['OrderedList', 'UnorderedList', '-', 'Outdent', 'Indent',
				'Blockquote', 'CreateDiv'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull'],
		['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select',
				'Button', 'ImageButton', 'HiddenField'],
		['Link', 'Unlink', 'Anchor'],
		['Image', 'Flash', 'Table', 'Rule', 'Smiley', 'SpecialChar',
				'PageBreak'], ['Style', 'FontFormat', 'FontName', 'FontSize'],
		['TextColor', 'BGColor'], ['FitWindow', 'ShowBlocks', '-', 'About'] // No
// comma
// for
// the
// last
// row.
];

// 是否开启简单功能与高级功能显示
if (typeof(FCKConfig.EnableAdvanceTable) == "undefined") { // 在页面中调用fckeditor时指定的
	// EnableAdvanceTable
	// 的值会先被调用。
	FCKConfig.EnableAdvanceTable = true; // 默认为true
}
FCKConfig.AdvanceTableNum = 0;
FCKConfig.AdvanceTable = [1, 3, 7, 8, 9, 12];

// 2. 添加中文字体
FCKConfig.FontNames = '宋体;楷体_GB2312;黑体;隶书;Times New Roman;Arial';
FCKConfig.FontSizes = '9/最小;12/较小;16/中等;20/较大;24/最大';

// 3. 修改 "回车" 和 "shift + 回车" 的样式
FCKConfig.EnterMode = 'br'; // p | div | br
FCKConfig.ShiftEnterMode = 'p'; // p | div | br

// 5. 设置允许上传的图片类型的扩展名列表
FCKConfig.ImageUploadAllowedExtensions = ".(jpg|gif|jpeg|png|bmp|abc)$"; // empty
// for
// all

// 其它需要修改的配置 ...

FCKConfig.LinkDlgHideTarget = true; // false ;
FCKConfig.LinkDlgHideAdvanced = true; // false ;

FCKConfig.ImageDlgHideLink = true; // false ;
FCKConfig.ImageDlgHideAdvanced = true; // false

FCKConfig.LinkUpload = false;