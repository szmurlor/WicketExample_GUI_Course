package PW;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public BasePage(final PageParameters parameters) {
		super(parameters);

		add(new Link<Void>("lnkDemo") {

			@Override
			public void onClick() {
				setResponsePage(DemoPage.class);
			}
		});
		add( new BookmarkablePageLink<Void>("lnkHome", HomePage.class));
    }
}
