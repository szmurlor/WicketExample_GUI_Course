package PW;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;

public class ProgressPullPage extends BasePage {
	private static final long serialVersionUID = 1L;

	protected String sProg = "0%";
	protected boolean stopped = false;
	protected boolean finished = false;

	public ProgressPullPage(final PageParameters parameters) {
		super(parameters);

		// add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		final WebMarkupContainer contPullProg;
		add(contPullProg = new WebMarkupContainer("contPullProg"));
		contPullProg.setOutputMarkupId(true);

		WebMarkupContainer prog;
		contPullProg.add( prog = new WebMarkupContainer("prog"));
		prog.add(new AttributeModifier("style", new LoadableDetachableModel<String>() {
			@Override
			protected String load() {
				return "width: " + sProg;
			}
		}));

		contPullProg.add(new Label("lblProg", new PropertyModel<String>(this, "sProg")));

		contPullProg.add(new AjaxSelfUpdatingTimerBehavior(Duration.milliseconds(1000)) {
			@Override
			protected void onPostProcessTarget(AjaxRequestTarget target) {
				super.onPostProcessTarget(target);
				if (stopped) {
					warn("Caution! The process has been breaked on.");
					target.add(feedback);
				}

				if (finished || stopped)
					stop(target);

				if (target != null) {
					target.add(contPullProg);
				}
			}
		});

		stopped = false;
		new Thread() {
			@Override
			public void run() {
				for (int i=0; i <= 100; i++) {
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					sProg = String.valueOf(i) + "%";
					if (stopped)
						break;
				}

				finished = true;
			}
		}.start();


		contPullProg.add(new IndicatingAjaxFallbackLink<Void>("cmdBreak") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				stopped = true;
			}

			@Override
			public boolean isVisible() {
				return !finished;
			}
		});

		contPullProg.add(new WebMarkupContainer("contFinished") {
			@Override
			public boolean isVisible() {
				return finished;
			}
		});
    }
}
