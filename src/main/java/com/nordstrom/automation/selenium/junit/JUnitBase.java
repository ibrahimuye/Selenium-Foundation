package com.nordstrom.automation.selenium.junit;

import java.lang.reflect.Method;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;

import com.nordstrom.automation.junit.ArtifactParams;
import com.nordstrom.automation.junit.RuleChainWalker;
import com.nordstrom.automation.selenium.core.TestBase;
import com.nordstrom.automation.selenium.model.Page;

/**
 * This abstract class implements the contract for Selenium Foundation test classes for JUnit.
 */
public abstract class JUnitBase implements TestBase, ArtifactParams {
    
    /** This method rule manages driver lifetimes and opens initial pages. */
    @Rule
    public final RuleChain ruleChain = RuleChain
            .outerRule(new ScreenshotCapture(this))
            .around(new PageSourceCapture(this))
            .around(DriverWatcher.getTestWatcher(this));
    
    private WebDriver driver = null;
    private Page initialPage = null;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<WebDriver> nabDriver() {
        return TestBase.optionalOf(driver);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setDriver(final WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Page> nabInitialPage() {
        return TestBase.optionalOf(initialPage);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setInitialPage(final Page pageObj) {
        initialPage = pageObj;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTest(final Method method) {
        return null != method.getAnnotation(Test.class);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBeforeMethod(final Method method) {
        return null != method.getAnnotation(Before.class);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAfterMethod(final Method method) {
        return null != method.getAnnotation(After.class);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBeforeClass(final Method method) {
        return null != method.getAnnotation(BeforeClass.class);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAfterClass(final Method method) {
        return null != method.getAnnotation(AfterClass.class);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Description getDescription() {
        return getLinkedRule(ScreenshotCapture.class).getDescription();
    }
    
    /**
     * Get the test rule of the specified type that's attached to the rule chain.
     * 
     * @param <T> test rule type
     * @param testRuleType test rule type
     * @return {@link ScreenshotCapture} test rule
     */
    public <T extends TestRule> T getLinkedRule(final Class<T> testRuleType) {
        Optional<T> optional = RuleChainWalker.getAttachedRule(ruleChain, testRuleType);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new IllegalStateException(testRuleType.getSimpleName() + " test rule wasn't found on the rule chain");
    }
}
