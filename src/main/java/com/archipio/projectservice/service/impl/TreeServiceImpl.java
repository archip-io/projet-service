package com.archipio.projectservice.service.impl;

import com.archipio.projectservice.exception.IncorrectChangeNodeTypeException;
import com.archipio.projectservice.exception.IncorrectParentNodeException;
import com.archipio.projectservice.exception.IncorrectRootNodeException;
import com.archipio.projectservice.exception.NotSuchNodeException;
import com.archipio.projectservice.persistence.entity.core.Node;
import com.archipio.projectservice.persistence.repository.NodeRepository;
import com.archipio.projectservice.service.TreeService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

@RequiredArgsConstructor
@Slf4j
public class TreeServiceImpl implements TreeService {
  private final NodeRepository nodeRepository;

  @Override
  public @NonNull Node createFolder(@NonNull String folderName) {
    Node node = new Node();
    node.setFileName(folderName);
    return node;
  }

  @Override
  public @NonNull Node createFileNode(@NonNull String fileName, @NonNull String fileId) {
    Node node = new Node();
    node.setFileName(fileName);
    node.setFileId(fileId);
    return node;
  }

  @Override
  public void addNode(@NonNull Node parentNode, @NonNull Node node) {
    if (parentNode.getFileId() != null) throw new IncorrectParentNodeException();
    node.setParent(parentNode);
    nodeRepository.save(node);
  }

  @Override
  public void deleteNode(@NonNull Node node) {
    if (!nodeRepository.existsById(node.getId())) throw new NotSuchNodeException();
    nodeRepository.delete(node);
  }

  @Override
  public @Nullable Node updateNode(@NonNull Node node) {
    Node parentNode = node.getParent();

    if (parentNode != null && parentNode.getFileId() != null)
      throw new IncorrectParentNodeException();
    else if (node.getFileId() != null) {
      List<Node> nodes = nodeRepository.findByParent(node);
      if (!nodes.isEmpty()) throw new IncorrectChangeNodeTypeException();
    }

    return nodeRepository.save(node);
  }

  @Override
  public Set<Node> getFullTree(@NonNull Node rootNode) {
    if (rootNode.getParent() != null) throw new IncorrectRootNodeException();
    Set<Node> tree = new HashSet<>();
    tree.add(rootNode);
    addChildNodes(tree, rootNode);
    return tree;
  }

  private void addChildNodes(Set<Node> tree, Node node) {
    for (Node child : node.getNodes()) {
      tree.add(child);
      addChildNodes(tree, child);
    }
  }
}
