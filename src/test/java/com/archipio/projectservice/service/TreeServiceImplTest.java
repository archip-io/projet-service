package com.archipio.projectservice.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

import com.archipio.projectservice.exception.IncorrectChangeNodeTypeException;
import com.archipio.projectservice.exception.IncorrectParentNodeException;
import com.archipio.projectservice.exception.IncorrectRootNodeException;
import com.archipio.projectservice.exception.NotSuchNodeException;
import com.archipio.projectservice.persistence.entity.core.Node;
import com.archipio.projectservice.persistence.repository.NodeRepository;
import com.archipio.projectservice.service.impl.TreeServiceImpl;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class TreeServiceImplTest {

  @Mock private NodeRepository nodeRepository;

  @InjectMocks private TreeServiceImpl treeService;

  @Test
  void createFolder_whenOkFolder_thenCreate() {
    // prepare
    final var folderName = "test";

    // do
    Node result = treeService.createFolder(folderName);

    // check
    assertThat(result).isNotNull();
    assertThat(result.getFileName()).isEqualTo(folderName);
  }

  @Test
  void createFileNode_whenOkFile_thenCreate() {
    // prepare
    final var fileName = "test";
    final var fileId = "testId";

    // do
    Node result = treeService.createFileNode(fileName, fileId);

    // check
    assertThat(result).isNotNull();
    assertThat(result.getFileName()).isEqualTo(fileName);
    assertThat(result.getFileId()).isEqualTo(fileId);
  }

  @Test
  void addNode_whenParentFolder_thenCreate() {
    // prepare
    final var folderName = "testFolder";
    final var fileName = "testFile";
    final var fileId = "testId";
    Node parent = treeService.createFolder(folderName);
    Node child = treeService.createFileNode(fileName, fileId);

    // do
    treeService.addNode(parent, child);

    // check
    verify(nodeRepository, times(1)).save(child);
  }

  @Test
  void addNode_whenParentFile_thenThrownIncorrectParentNodeException() {
    // prepare
    final var folderName = "testFolder";
    final var folderId = "testId";
    final var fileName = "testFile";
    final var fileId = "testId";
    Node parent = treeService.createFileNode(folderName, folderId);
    Node child = treeService.createFileNode(fileName, fileId);

    // do and check
    assertThatExceptionOfType(IncorrectParentNodeException.class)
        .isThrownBy(() -> treeService.addNode(parent, child));
  }

  @Test
  void updateNode_whenNewParentFile_thenThrownIncorrectParentNodeException() {
    // prepare
    final var folderName = "testFolder";
    final var fileName = "testFile";
    final var fileId = "testId";

    Node parent = treeService.createFolder(folderName);
    Node child = treeService.createFileNode(fileName, fileId);

    treeService.addNode(parent, child);
    parent.setFileId(fileId);

    // do and check
    assertThatExceptionOfType(IncorrectParentNodeException.class)
        .isThrownBy(() -> treeService.updateNode(child));
  }

  @Test
  void updateNode_whenChangeFolderToFile_thenThrownIncorrectChangeNodeTypeException() {
    // prepare
    final var folderName = "testFolder";
    final var fileName = "testFile";
    final var fileId = "testId";

    Node parent = treeService.createFolder(folderName);
    Node child = treeService.createFileNode(fileName, fileId);

    treeService.addNode(parent, child);
    parent.setFileId(fileId);

    when(nodeRepository.findByParent(parent)).thenReturn(List.of(child));

    // do and check
    assertThatExceptionOfType(IncorrectChangeNodeTypeException.class)
        .isThrownBy(() -> treeService.updateNode(parent));
  }

  @Test
  void updateNode_whenOkChange_thenUpdate() {
    // prepare
    final var fileName = "testFile";
    final var fileId = "testId";
    Node node = treeService.createFileNode(fileName, fileId);
    when(nodeRepository.save(node)).thenReturn(node);

    // do
    Node result = treeService.updateNode(node);

    // check
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(node);
    verify(nodeRepository, times(1)).save(node);
  }

  @Test
  void deleteNode_whenNodeExist_thenDelete() {
    // prepare
    final var fileName = "testFile";
    final var fileId = "testId";
    final var uuid = UUID.randomUUID();

    Node child = treeService.createFileNode(fileName, fileId);
    child.setId(uuid);

    when(nodeRepository.existsById(child.getId())).thenReturn(true);
    doNothing().when(nodeRepository).delete(child);

    // do
    treeService.deleteNode(child);

    // check
    verify(nodeRepository, times(1)).delete(child);
  }

  @Test
  void deleteNode_whenNodeNotExist_thenThrownNotSuchNodeException() {
    // prepare
    final var fileName = "test";
    final var fileId = "testId";
    Node result = treeService.createFileNode(fileName, fileId);

    // check
    assertThatExceptionOfType(NotSuchNodeException.class)
        .isThrownBy(() -> treeService.deleteNode(result));
    verify(nodeRepository, times(0)).delete(result);
  }

  @Test
  void getFullTree_whenOkRootNode_thenGetFullTree() {
    // prepare
    final var folderName = "testFolder";
    final var fileName = "test";
    final var fileId = "testId";

    Node parent = treeService.createFolder(folderName);
    Set<Node> set = new HashSet<>();

    for (int i = 0; i < 3; i++) {
      Node file = treeService.createFileNode(fileName + i, fileId + i);
      set.add(file);
    }
    parent.setNodes(set);

    // do
    Set<Node> result = treeService.getFullTree(parent);

    // check
    Set<Node> equalSet = new HashSet<>(set);
    equalSet.add(parent);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(equalSet);
  }

  @Test
  void getFullTree_whenRootHaveParent_thenThrownIncorrectRootNodeException() {
    // prepare
    final var folderName = "testFolder";
    final var fileName = "test";
    final var fileId = "testId";

    Node parent = treeService.createFolder(folderName);
    Node file = treeService.createFileNode(fileName, fileId);
    parent.setParent(file);

    // do check
    assertThatExceptionOfType(IncorrectRootNodeException.class)
        .isThrownBy(() -> treeService.getFullTree(parent));
  }
}
