package com.matthewfraser.cp470_losty;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProfileInstrumentedTest {

    @Rule
    public ActivityScenarioRule<Login> mActivityScenarioRule =
            new ActivityScenarioRule<>(Login.class);

    @Test
    public void profileInstrumentedTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.accountCreationButton), withText("Create Account"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                7),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.enterAccountName),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("alexusername"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.enterAccountEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("alex@alex.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.enterAccountPhoneNumber),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                6),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("1234561234"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.enterAccountUsername),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                8),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("alexusername"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.enterAccountPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                10),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("alexusername1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.enterAccountPassword), withText("alexusername1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                10),
                        isDisplayed()));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.accountCreationButton), withText("Create Account"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                11),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.enterLoginUsername),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("alexusername"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.enterLoginPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("alexusername1"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.loginButton), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.action_three), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.profilePictureImageView),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.profilePictureTextView), withText("Profile Picture"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.updateEmailTextView), withText("Update Email"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.updateEmailTextView), withText("Update Email"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.editTextTextEmailAddress), withText("alex@alex.com"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.nameTextView), withText("Name"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.editTextTextPersonName), withText("alexusername"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.phoneNumberTextView), withText("Phone Number"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.editTextPhone), withText("1234561234"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.saveButton), withText("SAVE"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.signoutButton), withText("SIGN OUT"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.profilePictureImageView),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0)));
        appCompatImageView.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
