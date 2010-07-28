/**
 * VersionRepositoryTest.java

 */
package com.porpoise.gen.version;

import com.porpoise.gen.test.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * VersionRepositoryTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class VersionRepositoryTest
{
    private VersionedByInt<String> key;
    private VersionControl         versionControl;
    private VersionRepository      repository;

    /**
     * 
     */
    @Before
    public final void setup()
    {
        this.repository = new VersionRepository();
        this.versionControl = new VersionControl(this.repository);
        final int someUniqueId = 456;
        this.key = new VersionedByInt<String>("one", someUniqueId);

    }

    /**
     * @throws NotUnderVersionControlException
     * 
     */
    @Test(expected = NotUnderVersionControlException.class)
    public void test_cantCheckOutAnObjectNotUnderVersionControl() throws NotUnderVersionControlException
    {
        new VersionControl().checkOut(new VersionedByInt<String>("one", 1));
        Assert.fail("Nothing should've been available to check out");
    }

    /**
     * @throws ObjectOutOfDateException
     * @throws NotUnderVersionControlException
     * 
     */
    @Test
    public void test_submitSimpleCase() throws ObjectOutOfDateException,
            NotUnderVersionControlException
    {
        //
        // test a simple submit
        //
        Assert.assertFalse(this.versionControl.isUnderVersionControl(this.key));
        try
        {
            this.versionControl.getLatestVersion(this.key);
            Assert.fail("Not under version control expected");
        }
        catch (final NotUnderVersionControlException exp)
        {
            // the key we're checking isn't under version control
        }

        Assert.assertEquals(1, this.versionControl.submit(this.key, 0));
        Assert.assertEquals(1, this.versionControl.getLatestVersion(this.key));
    }

    /**
     * @throws ObjectOutOfDateException
     * @throws NotUnderVersionControlException
     * 
     */
    @Test
    public void test_checkOutSimpleCase() throws ObjectOutOfDateException,
            NotUnderVersionControlException
    {
        submitAndCheckOut(this.versionControl, this.key, 0);
    }

    /**
     * @throws ObjectOutOfDateException
     * @throws NotUnderVersionControlException
     * 
     */
    @Test
    public void test_multipleCheckOutAndSubmit() throws ObjectOutOfDateException,
            NotUnderVersionControlException
    {
        submitAndCheckOut(this.versionControl, this.key, 0);
        this.key.set("two");
        submitAndCheckOut(this.versionControl, this.key, 1);
        this.key.set("three");
        submitAndCheckOut(this.versionControl, this.key, 2);

        Assert.assertEquals("one", this.repository.getObject(this.key, 1));
        Assert.assertEquals("two", this.repository.getObject(this.key, 2));
        Assert.assertEquals("three", this.repository.getObject(this.key, 3));

        this.versionControl.submit(new VersionedByInt<String>("some other object",
                123),
                0);

        System.out.println(this.repository);
    }

    /**
     * 
     * @throws ObjectOutOfDateException
     * @throws NotUnderVersionControlException
     * @throws UnknownRevisionException
     */
    @Test
    public void test_revert() throws ObjectOutOfDateException,
            NotUnderVersionControlException,
            UnknownRevisionException
    {
        submitAndCheckOut(this.versionControl, this.key, 0);
        this.key.set("two");
        final int stillCheckedOutAtThisRevision = this.versionControl.revert(this.key,
                1);
        Assert.assertEquals(0, stillCheckedOutAtThisRevision);

        //
        // try checking three more out, just so we can revert them
        //
        this.versionControl.checkOut(this.key);
        this.versionControl.checkOut(this.key);
        this.versionControl.checkOut(this.key);

        Assert.assertEquals(2, this.versionControl.revert(this.key, 1));
        Assert.assertEquals(1, this.versionControl.revert(this.key, 1));
        Assert.assertEquals(0, this.versionControl.revert(this.key, 1));

        try
        {
            Assert.assertEquals(0, this.versionControl.revert(this.key, 1));
            Assert.fail("UnknownRevisionException is expected after no more checked out instances are available");
        }
        catch (final UnknownRevisionException e)
        {
            // our version control doesn't think any files are checked out
            // anymore at this
            // revision, so here we expect it to throw an
            // UnknownRevisionException
        }
    }

    /**
     * @param vc
     * @param versionedString
     * @throws ObjectOutOfDateException
     * @throws NotUnderVersionControlException
     */
    private void submitAndCheckOut(final VersionControl vc,
            final VersionedByInt<String> versionedString,
            final int revision) throws ObjectOutOfDateException,
            NotUnderVersionControlException
    {
        //
        // submit, then check out. Test the revisions.
        //
        Assert.assertEquals(revision + 1, vc.submit(versionedString, revision));
        Assert.assertEquals(versionedString.get(), vc.checkOut(versionedString));
        Assert.assertEquals(revision + 1, vc.getLatestVersion(versionedString));
    }
}
