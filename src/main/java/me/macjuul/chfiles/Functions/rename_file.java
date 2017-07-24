package me.macjuul.chfiles.Functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.Security;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREIOException;
import com.laytonsmith.core.exceptions.CRE.CRESecurityException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;

import java.io.File;

/**
 * Created by bexco on 2016-03-16.
 */
@api
public class rename_file extends AbstractFunction {
    @Override
    public Class<? extends CREThrowable>[] thrown() {
        return new Class[]{
                CREIOException.class,
                CRESecurityException.class
        };
    }

    @Override
    public boolean isRestricted() {
        return true;
    }

    @Override
    public Boolean runAsync() {
        return true;
    }

    @Override
    public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {
        File loc = Static.GetFileFromArgument(args[0].val(), env, t, null);
        if (!Security.CheckSecurity(loc)) {
            throw new CRESecurityException("You do not have permission to access the file '" + loc.getAbsolutePath() + "'", t);
        }
        if (!loc.exists()) {
            throw new CREIOException(loc.getAbsolutePath() + "Doesn't exists", t);
        }
        if (loc.isDirectory()) {
            loc.renameTo(new File(loc.getParent() + File.pathSeparator + args[1].val() + File.pathSeparator));
        } else if (loc.isFile()) {
            loc.renameTo(new File(loc.getParent() + File.pathSeparator + args[1].val()));
        }
        return CVoid.VOID;
    }

    @Override
    public String getName() {
        return "rename_file";
    }

    @Override
    public Integer[] numArgs() {
        return new Integer[]{1};
    }

    @Override
    public String docs() {
        return "{file, name} void rename a file.";
    }

    @Override
    public Version since() {
        return new SimpleVersion(1, 0, 0);
    }
}
