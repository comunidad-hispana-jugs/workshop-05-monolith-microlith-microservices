package org.ecjug.hackday.api;

import org.ecjug.hackday.domain.model.Member;

import java.util.List;

/**
 * @author Kleber Ayala
 */
public interface MembersService {

    List<Member> list();

    Member add(Member member);
}
