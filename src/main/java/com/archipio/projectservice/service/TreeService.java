package com.archipio.projectservice.service;

import com.archipio.projectservice.exception.IncorrectParentNodeException;
import com.archipio.projectservice.persistence.entity.core.Node;
import java.util.Set;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public interface TreeService {

  /**
   * Создание узла-папки
   *
   * @param folderName название папки
   * @return папка узел
   */
  @NonNull
  Node createFolder(@NonNull String folderName);

  /**
   * Создание узла-файла
   *
   * @param fileName название файла
   * @param fileId uuid файла
   * @return файл-узел
   */
  @NonNull
  Node createFileNode(@NonNull String fileName, @NonNull String fileId);

  /**
   * Создание нового узла
   *
   * @param parentNode родительский узел
   * @param node узел, который надо сохранить в дерево
   * @throws IncorrectParentNodeException ошибка при попытке назначить родителем папку
   */
  void addNode(@NonNull Node parentNode, @NonNull Node node);

  /**
   * Удаление узла.
   *
   * <p>Потомки удаляеются автоматически, логика на уровне бд.
   *
   * @param node узел
   */
  void deleteNode(@NonNull Node node);

  /**
   * Редактированеи узла.
   *
   * @param node узел
   * @return обновленный узел
   */
  @Nullable
  Node updateNode(@NonNull Node node);

  /**
   * Получение дерева проекта по корневому узлу.
   *
   * @param rootNode корневой узел
   * @return полное множество узлов дерева
   */
  Set<Node> getFullTree(@NonNull Node rootNode);
}
