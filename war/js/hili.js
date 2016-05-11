highlightWord(document.body,"para");

function highlightWord(root, word) {
  removeHighlights(root);
  textNodesUnder(root).forEach(highlightWords);

  function textNodesUnder(root) {
    var walk = document.createTreeWalker(root, NodeFilter.SHOW_TEXT, null, false),
      text = [],
      node;
    while (node = walk.nextNode()) text.push(node);
    return text;
  }

  function highlightWords(n) {
    for (var i;
      (i = n.nodeValue.indexOf(word, i)) > -1; n = after) {
      var after = n.splitText(i + word.length);
      var highlighted = n.splitText(i);
      var span = document.createElement('span');
      span.className = 'highlighted';
      span.appendChild(highlighted);
      after.parentNode.insertBefore(span, after);
    }
  }
}

function removeHighlights(root){     
  [].forEach.call(root.querySelectorAll('span.highlighted'),function(el){
    el.parentNode.replaceChild(el.firstChild,el);
  });
}
