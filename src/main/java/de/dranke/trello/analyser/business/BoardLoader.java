package de.dranke.trello.analyser.business;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Argument;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Member;
import com.julienvey.trello.domain.TList;
import de.dranke.trello.analyser.model.TrelloBoard;
import de.dranke.trello.analyser.model.TrelloList;

import java.util.ArrayList;
import java.util.List;

public class BoardLoader {

  private final Trello client;

  public BoardLoader(Trello client) {
    this.client = client;
  }

  public List<TrelloBoard> load() {
    final Member me = client.getMemberInformation("me");
    final ArrayList<TrelloBoard> result = new ArrayList<>();
    me.getIdBoards().stream().forEach((id) -> {
      final TrelloBoard trelloBoard = loadInternal(id);
      trelloBoard.setLists(loadLists(id));
      result.add(trelloBoard);
    });

    return result;
  }

  private TrelloBoard loadInternal(String boardId) {
    final Board board = client.getBoard(boardId, new Argument("fields", "name"));
    TrelloBoard trelloBoard = createTrelloBoard(board);
    return trelloBoard;
  }

  private TrelloBoard createTrelloBoard(Board board) {
    final TrelloBoard result = new TrelloBoard(board.getId());
    result.setTitle(board.getName());
    return result;
  }

  private List<TrelloList> loadLists(String boardId) {
    final ArrayList<TrelloList> result = new ArrayList<>();
    final List<TList> lists = client.getBoardLists(boardId, new Argument("fields", "name"));
    for (TList list : lists) {
      result.add(createTrelloList(list));
    }
    return result;
  }

  private TrelloList createTrelloList(TList list) {
    final TrelloList result = new TrelloList(list.getId());
    result.setTitle(list.getName());
    return result;
  }
}
