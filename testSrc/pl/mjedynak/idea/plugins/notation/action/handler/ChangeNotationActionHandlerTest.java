package pl.mjedynak.idea.plugins.notation.action.handler;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.junit.Before;
import org.junit.Test;
import pl.mjedynak.idea.plugins.notation.converter.NotationConverter;

import static org.mockito.Mockito.*;

public class ChangeNotationActionHandlerTest {

    private static final String CAMEL_CASE_TEXT = "camelCaseText";
    private static final String UNDER_SCORE_TEXT = "UNDER_SCORE_TEXT";
    private static final int SELECTION_START = 0;
    private static final int SELECTION_END = 1;

    private ChangeNotationActionHandler changeNotationActionHandler;
    private NotationConverter notationConverter;
    private DataContext dataContext;
    private Editor editor;
    private SelectionModel selectionModel;

    @Before
    public void setUp() {
        notationConverter = mock(NotationConverter.class);
        changeNotationActionHandler = new ChangeNotationActionHandler(notationConverter);
        dataContext = mock(DataContext.class);
        editor = mock(Editor.class);
        selectionModel = mock(SelectionModel.class);
        Document document = mock(Document.class);

        when(editor.getSelectionModel()).thenReturn(selectionModel);
        when(editor.getDocument()).thenReturn(document);
        when(selectionModel.getSelectionStart()).thenReturn(SELECTION_START);
        when(selectionModel.getSelectionEnd()).thenReturn(SELECTION_END);
    }

    @Test
    public void shouldChangeCamelCaseTextInModalWindowToUnderscore() {
        // given
        when(selectionModel.getSelectedText()).thenReturn(CAMEL_CASE_TEXT);

        // when
        changeNotationActionHandler.executeWriteAction(editor, dataContext);

        // then
        verify(notationConverter).convertToUnderscoreUpperCase(CAMEL_CASE_TEXT);
    }

    @Test
    public void shouldChangeUnderscoreTextInModalWindowToCamelCase() {
        // given
        when(selectionModel.getSelectedText()).thenReturn(UNDER_SCORE_TEXT);

        // when
        changeNotationActionHandler.executeWriteAction(editor, dataContext);

        // then
        verify(notationConverter).convertToCamelCase(UNDER_SCORE_TEXT);
    }

}
